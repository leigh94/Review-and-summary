const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("Selfdestruct Attack Demo", function () {
  let attacker, deployer, victim, attackContract;

  beforeEach(async function () {
    [deployer, attacker] = await ethers.getSigners();

    const Victim = await ethers.getContractFactory("Victim", deployer);
    victim = await Victim.deploy();

    const AttackSelfDestruct = await ethers.getContractFactory("AttackSelfDestruct", attacker);
    attackContract = await AttackSelfDestruct.deploy({
      value: ethers.parseEther("1") 
    });
  });

  it("Victim should receive ETH forcibly via selfdestruct", async function () {
    expect(await victim.getBalance()).to.equal(0n); // v6：bigint
    expect(await victim.isRich()).to.equal(false);

    await attackContract.attack(victim.getAddress());

    expect(await victim.getBalance()).to.equal(ethers.parseEther("1"));
    expect(await victim.isRich()).to.equal(true);
  });
});
