// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/utils/Address.sol";



// 逻辑合约
contract Logic {
    address public owner;
    
    function becomeOwner() public {
        owner = msg.sender;
    }
}

// 代理合约
contract DelegatecallHijack {
    address public implementation;

    constructor(address _implementation){
        require(_implementation != address(0), "implementation contract address cannot be zero");
        implementation = _implementation;
    }

    function upgrade(address newImplementation) public {
        implementation = newImplementation;
    }

    fallback() external payable {
        address impl = implementation;
        require(impl != address(1) ,"Not Set Impl addr");

        (bool success , ) = impl.delegatecall(msg.data);

        require(success,"delegatecall failed");
    }
}


// 攻击合约
contract EvilLogic {
    address public owner;

    function pwn() public {
        Logic logic = Logic(address(this));
        logic.becomeOwner(); // 攻击者的地址会成为 owner
    }
}

contract SafeDelegatecall {
    address public implementation;
    address public admin;

    modifier onlyAdmin() {
        require(msg.sender == admin , "Not admin");
        _;
    }

    constructor(address _implementation) {
        require(isContract(_implementation), "Not contract");
        implementation = _implementation;
        admin = msg.sender;
    } 

    function upgradeTo(address _newImplementation) external onlyAdmin {
        require(isContract(_newImplementation), "Not contract");
        implementation = _newImplementation;
    }

    function isContract(address account) internal view returns (bool) {
        return account.code.length > 0;
    }

    fallback() external payable {
        address impl = implementation;
        require(impl != address(0), "Implementation not set");

        (bool success, ) = impl.delegatecall(msg.data);
        require(success, "delegatecall failed");
    }

    receive() external payable {
        // Optional: allow receiving ETH
    }
}