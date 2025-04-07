const { ethers, upgrades } = require("hardhat");

async function main() {
  const [deployer] = await ethers.getSigners();

  const ImplementationV1 = await ethers.getContractFactory("contracts/uups/ImplementationV1.sol:ImplementationV1");

  console.log("Deploying proxy...");
  const implementationV1 = await upgrades.deployProxy(ImplementationV1, [42], {
    initializer: "initialize",
  });

  await implementationV1.waitForDeployment(); // 新版 Hardhat 用这个替代 .deployed()
  console.log("Proxy deployed to:", await implementationV1.getAddress());


  // 调用 setValue 函数进行状态更新
  const tx = await implementationV1.setValue(10); // 传递新值 10
  await tx.wait(); // 等待交易确认

  // 读取代理合约中的 value
  const updatedValue = await implementationV1.value(); // 获取新的 value
  console.log("Updated value:", updatedValue.toString()); // 打印新的 value


  // 升级
  const ImplementationV2 = await ethers.getContractFactory("contracts/uups/ImplementationV2.sol:ImplementationV2");

  console.log("Upgrading proxy...");
  const upgraded = await upgrades.upgradeProxy(await implementationV1.getAddress(), ImplementationV2);

  console.log("Proxy upgraded. Address still:", await upgraded.getAddress());

    // 调用 setValue 函数进行状态更新
    const txv2 = await upgraded.setValue(10); // 传递新值 10
    await txv2.wait(); // 等待交易确认
  
    // 读取代理合约中的 value
    const valuev2 = await upgraded.value(); // 获取新的 value
    console.log("upgrade value:", valuev2.toString()); // 打印新的 value

//   const version = await upgraded.getVersion();
//   console.log("Version after upgrade:", version);

    const version = await upgraded.version();
    console.log("version value:", version.toString());

}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
