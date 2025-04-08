// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

/// @title 初始化项目 本地测试
/// @author Leigh
/// @notice 测试
/// @dev Test
contract LocalTestContract {
    string public message;

    event MessageChanged(string newMessage);

    function setMessage(string memory _newmessage) public {
        message = _newmessage;
        emit MessageChanged(message);
    }

    function getMessage() public view returns (string memory){
        return message;
    }
}