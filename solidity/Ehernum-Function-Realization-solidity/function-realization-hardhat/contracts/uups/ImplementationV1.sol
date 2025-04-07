// SPDX-License-Identifier: MIT
pragma solidity 0.8.29;

import "@openzeppelin/contracts-upgradeable/proxy/utils/UUPSUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";

contract ImplementationV1 is UUPSUpgradeable, OwnableUpgradeable {
    uint256 public value;

    function initialize(uint256 _value) public initializer {
        __Ownable_init(msg.sender); // 初始化所有者（默认使用部署者）
        value = _value;
    }

    function setValue(uint256 _value) public onlyOwner {
        value = _value;
    }

    // UUPS升级授权函数
    function _authorizeUpgrade(address newImplementation) internal override onlyOwner {}
}
