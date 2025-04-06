// SPDX-License-Identifier: MIT
pragma solidity 0.8.29;

contract ImplementationV2 {
    // 必须与V1完全相同的变量声明顺序
    string public message;
    
    // 新变量必须追加在最后
    uint public version;
    
    // 移除构造函数中的初始化
    constructor() {
        version = 2; // 仅初始化新增变量
    }

    function setMessage(string memory _message) public {
        message = _message;
    }

    function getVersion() public view returns (uint) {
        return version;
    }
}