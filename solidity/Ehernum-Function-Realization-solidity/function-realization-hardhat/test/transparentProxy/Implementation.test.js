const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("ImplementationV1 Standalone Test", function () {
  let implementationV1;
  let owner, user;
  let provider; // 添加provider引用

  before(async function () {
    [owner, user] = await ethers.getSigners();
    provider = ethers.provider; // 初始化provider
    
    const ImplementationV1 = await ethers.getContractFactory("ImplementationV1");
    implementationV1 = await ImplementationV1.deploy("Initial Message");
    await implementationV1.waitForDeployment();
  });

  // ...其他测试用例保持不变...

  it("should have correct storage layout", async function () {
    const contractAddress = await implementationV1.getAddress();
    
    // 检查slot 0 (string的存储指针)
    const slot0 = await provider.getStorage(
      contractAddress,
      "0x0000000000000000000000000000000000000000000000000000000000000000"
    );
    console.log("Slot 0:", slot0);

    // 计算字符串实际存储位置
    const stringSlot = ethers.keccak256(
      "0x0000000000000000000000000000000000000000000000000000000000000000"
    );
    const storedData = await provider.getStorage(contractAddress, stringSlot);
    console.log("String storage:", storedData);
    
    // 验证长度 (Solidity字符串存储格式: 长度*2 + 1)
    const length = parseInt(slot0, 16) - 1 / 2;
    expect(length).to.equal("Initial Message".length);
  });
});