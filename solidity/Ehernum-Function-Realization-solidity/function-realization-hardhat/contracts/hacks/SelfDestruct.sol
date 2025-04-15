// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

// 受害合约
contract Victim {
    // 返回合约当前余额
    function getBalance() external view returns(uint) {
        return address(this).balance;
    }

    // 判断合约是否有钱
    function isRich() external view returns (bool) {
        return address(this).balance >= 1 ether;
    }
}

contract AttackSelfDestruct {
    constructor() payable {}

    //开始攻击
    function attack(address payable target) external {
        selfdestruct(target);
    }
}

contract VictimSafe {
    mapping(address => uint) public deposits;

    function deposit() external payable{
        deposits[msg.sender] += msg.value;
    }

    function isRich(address addr) external view returns (bool) {
        return deposits[addr] >= 1 ether;
    }
}
