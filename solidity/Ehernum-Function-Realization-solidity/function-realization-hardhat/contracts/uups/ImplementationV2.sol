// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/proxy/utils/UUPSUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";

contract ImplementationV2 is UUPSUpgradeable, OwnableUpgradeable {
    uint256 public value;
    string public version;

    function initialize(uint256 _value) public initializer {
        __Ownable_init(msg.sender);
        value = _value;
        version = "V2"; // 新版本标识
    }

    function setValue(uint256 _value) public onlyOwner {
        value = _value * 2;
    }

    function getVersion() public view returns (string memory) {
        return version;
    }

    // UUPS upgrade function
    function _authorizeUpgrade(address newImplementation) internal override onlyOwner {}
}
