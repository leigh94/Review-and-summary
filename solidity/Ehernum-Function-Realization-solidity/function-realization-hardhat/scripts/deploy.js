const hre = require("hardhat");

async function main() {
    const [deployer] = await hre.ethers.getSigners();
    console.log("Deploying contracts with the account:", deployer.address);


    const LocalTestContract = await hre.ethers.getContractFactory("LocalTestContract");
    const contract = await LocalTestContract.deploy();
    
    console.log("LocalTestContract type:", typeof LocalTestContract);
    console.log("contract type:", typeof contract);
    console.log("contract keys:", Object.keys(contract));
    console.log("✅ Contract deployed to:", contract.target);
}

main()
    .then(() => process.exit(0))
    .catch((error) => {
    console.error(error);
    process.exit(1);
});