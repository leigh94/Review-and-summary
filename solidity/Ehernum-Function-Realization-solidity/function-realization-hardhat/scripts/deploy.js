const hre = require("hardhat");

async function main() {
    const MyContract = await hre.ethers.getContractFactory("MyContract");
    const contract = await MyContract.deploy("Hello Lee!");
    
    console.log("MyContract type:", typeof MyContract);
    console.log("contract type:", typeof contract);
    console.log("contract keys:", Object.keys(contract));
    console.log("✅ Contract deployed to:", contract.target);
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});