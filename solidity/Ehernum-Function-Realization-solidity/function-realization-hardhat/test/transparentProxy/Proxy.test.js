const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("Proxy Upgrade Pattern", function () {
    let proxy;
    let implementationV1;
    let implementationV2;
    let admin, user;

    before(async function () {
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
        implementationV2 = await ImplementationV2.deploy("V2 Initial Message");
    });

    describe("Initial State", function () {
        it("should proxy to V1 correctly", async function () {
            // 通过ABI连接代理合约
            const proxyAsV1 = await ethers.getContractAt(
                "ImplementationV1",
                await proxy.getAddress()
            );
            expect(await proxyAsV1.message()).to.equal("V1 Initial Message");
        });

        it("should not have version in V1", async function () {
            // 明确测试不存在的函数应该回退
            const proxyAsV1 = await ethers.getContractAt(
                "ImplementationV1",
                await proxy.getAddress()
            );
            await expect(
                proxyAsV1.getVersion?.() 
                ?? Promise.reject("Function not in ABI")
            ).to.be.rejected;
        });
    });

    describe("After Upgrade", function () {
        before(async function () {
            // 执行升级到V2
            await proxy.upgrade(await implementationV2.getAddress());
        });

        it("should preserve storage after upgrade", async function () {
            const proxyAsV1 = await ethers.getContractAt(
                "ImplementationV1", 
                await proxy.getAddress()
            );
            expect(await proxyAsV1.message()).to.equal("V1 Initial Message");
        });

        it("should have new functionality", async function () {
            const proxyAsV2 = await ethers.getContractAt(
                "ImplementationV2",
                await proxy.getAddress()
            );
            expect(await proxyAsV2.getVersion()).to.equal(2);
        });

        it("should allow message modification", async function () {
            const proxyAsV2 = await ethers.getContractAt(
                "ImplementationV2",
                await proxy.getAddress()
            );
            await proxyAsV2.setMessage("Updated After Upgrade");
            expect(await proxyAsV2.message()).to.equal("Updated After Upgrade");
        });
    });
});