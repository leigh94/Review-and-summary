// SPDX-License-Identifier: MIT
pragma solidity 0.8.29;

/// @title 初始化项目 测试
/// @author Leigh
/// @notice 测试
/// @dev Test
contract MyContract {
    string public message;

    constructor(string memory _message) {
        message = _message;
    }

    function setMessage(string memory _newmessage) public {
        message = _newmessage;
    }

    function getMessage() public view returns (string memory){
        return message;
    }
}