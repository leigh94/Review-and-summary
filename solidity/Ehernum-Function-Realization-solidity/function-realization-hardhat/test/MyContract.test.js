const { expect } = require("chai");

describe("MyContract",function(){
    it("Should return the initial message", async function () {
        const MyContract = await ethers.getContractFactory("MyContract");
        const contract = await MyContract.deploy("Hello Lee!");

        expect(await contract.message()).to.equal("Hello Lee!");
    });

    it("Should update the message",async function () {
        const MyContract = await ethers.getContractFactory("MyContract");
        const contract = await MyContract.deploy("Initial!");

        await contract.setMessage("Updated");
        expect(await contract.message()).to.equal("Updated");
    });

});