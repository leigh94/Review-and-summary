const { expect } = require("chai");
const { ethers } = require("hardhat");


describe("Reentrancy Attack from ReEntrancy.sol", function () {
  let deployer, alice, bob, eve;
  let etherStore, etherStoreFixed, attack, attackFixed;

  beforeEach(async () => {
    [deployer, alice, bob, eve] = await ethers.getSigners();
    const DEPOSIT_AMOUNT = ethers.utils.parseEther("1");
    
    const EtherStore = await ethers.getContractFactory("EtherStore");
    const EtherStoreFixed = await ethers.getContractFactory("EtherStoreFixed");
    const Attack = await ethers.getContractFactory("Attack", eve);

    etherStore = await EtherStore.deploy();
    etherStoreFixed = await EtherStoreFixed.deploy();

    // Alice 和 Bob 存款到易受攻击合约
    await etherStore.connect(alice).deposit({ value: DEPOSIT_AMOUNT });
    await etherStore.connect(bob).deposit({ value: DEPOSIT_AMOUNT });

    // Alice 和 Bob 存款到修复版合约
    await etherStoreFixed.connect(alice).deposit({ value: DEPOSIT_AMOUNT });
    await etherStoreFixed.connect(bob).deposit({ value: DEPOSIT_AMOUNT });

    attack = await Attack.deploy(etherStore.address);
    attackFixed = await Attack.deploy(etherStoreFixed.address);
  });

  it("🔴 攻击成功：vulnerable EtherStore", async () => {
    console.log("\n==== 攻击前状态 ====");
    console.log("EtherStore 余额:", ethers.utils.formatEther(await etherStore.getBalance()), "ETH");
    console.log("Attack 余额:", ethers.utils.formatEther(await attack.getBalance()), "ETH");

    // 发起攻击
    await attack.connect(eve).attack({ value: DEPOSIT_AMOUNT });

    console.log("\n==== 攻击后状态 ====");
    console.log("EtherStore 余额:", ethers.utils.formatEther(await etherStore.getBalance()), "ETH");
    console.log("Attack 余额:", ethers.utils.formatEther(await attack.getBalance()), "ETH");

    expect(await ethers.provider.getBalance(etherStore.address)).to.equal(0);
    expect(await attack.getBalance()).to.equal(ethers.utils.parseEther("3"));
  });

  it("🟢 攻击失败：fixed EtherStore", async () => {
    console.log("\n==== 修复合约攻击前 ====");
    console.log("EtherStoreFixed 余额:", ethers.utils.formatEther(await etherStoreFixed.getBalance()), "ETH");
    console.log("AttackFixed 余额:", ethers.utils.formatEther(await attackFixed.getBalance()), "ETH");

    // 发起攻击
    await attackFixed.connect(eve).attack({ value: DEPOSIT_AMOUNT });

    console.log("\n==== 修复合约攻击后 ====");
    console.log("EtherStoreFixed 余额:", ethers.utils.formatEther(await etherStoreFixed.getBalance()), "ETH");
    console.log("AttackFixed 余额:", ethers.utils.formatEther(await attackFixed.getBalance()), "ETH");

    expect(await etherStoreFixed.getBalance()).to.equal(ethers.utils.parseEther("2"));
    expect(await attackFixed.getBalance()).to.equal(ethers.utils.parseEther("1"));
  });
});
