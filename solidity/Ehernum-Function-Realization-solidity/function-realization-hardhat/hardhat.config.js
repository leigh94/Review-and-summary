require('@openzeppelin/hardhat-upgrades');
require("@nomicfoundation/hardhat-toolbox");

/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
  solidity: {
    compilers: [
      {
        version: "0.7.6"
      },
      {
        version: "0.8.20"
      },
      {
        version: "0.8.26"
      },
      {
        version: "0.8.29"
      }
    ]
  }
};
