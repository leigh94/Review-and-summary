const hre = require("hardhat");

async function main() {
    [admin, user] = await ethers.getSigners();

    // 部署V1实现合约
    const ImplementationV1 = await ethers.getContractFactory("ImplementationV1");
    implementationV1 = await ImplementationV1.deploy("V1 Initial Message");

    // 部署代理合约
    const Proxy = await ethers.getContractFactory("Proxy");
    proxy = await Proxy.deploy(
        await implementationV1.getAddress(),
        admin.address
    );

    // 部署V2实现合约
    const ImplementationV2 = await ethers.getContractFactory("ImplementationV2");
    implementationV2 = await ImplementationV2.deploy();

    // 通过ABI连接代理合约
    const proxyAsV1 = await ethers.getContractAt(
        "ImplementationV1",
        await proxy.getAddress()
    );
    expect(await proxyAsV1.message()).to.equal("V1 Initial Message");

    // 明确测试不存在的函数应该回退
    await expect(
        proxyAsV1.getVersion?.()
        ?? Promise.reject("Function not in ABI")
    ).to.be.rejected;

    // 执行升级到V2
    await proxy.upgrade(await implementationV2.getAddress());
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});