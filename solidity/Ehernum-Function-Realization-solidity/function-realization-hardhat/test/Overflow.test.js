const {expect} = require("chai");
const {ethers} = require("hardhat");

describe("IntInteger Overflow Attack Demo",function () {
    let timeLock, attackContract;
    let deployer, attacker;

    beforeEach(async () => {
        [deployer,attacker] = await ethers.getSigner();

        const TimeLock = await ethers.getContractFactory("TimeLock");
        timeLock = await TimeLock.deploy();
        await timeLock.deployed();

        const Attack = await ethers.getContractFactory("Attack",attacker);
        attackContract = await Attack.deploy(timeLock.getAddress())
        await attackContract.deployed();
    });

    it("should bypass lockTime using overflow and withdraw immediately",async () => {
        const depositTx = await attackContract.connect(attacker).attack({
            value:ethers.utils.parseEther("1"),
        });
        await depositTx.wait();

        // 验证攻击合约余额是否增加了
        const balance = await ethers.provider.getBalance(attackContract.getAddress());
        expect(balance).to.equal(ethers.utils.parseEther("1"));
    });
});