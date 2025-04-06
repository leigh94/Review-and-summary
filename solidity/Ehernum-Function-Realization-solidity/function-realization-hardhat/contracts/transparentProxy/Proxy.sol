// SPDX-License-Identifier: MIT
pragma solidity 0.8.29;

contract Proxy {
    address private _implementation;
    address private _admin;
    
    event Upgraded(address indexed implementation);

    constructor(address implementation, address admin) {
        _implementation = implementation;
        _admin = admin;
    }
    
    modifier onlyAdmin() {
        require(msg.sender == _admin, "Proxy: Caller is not admin");
        _;
    }

    function upgrade(address newImplementation) external onlyAdmin {
        require(newImplementation != address(0), "Proxy: Invalid implementation");
        _implementation = newImplementation;
        emit Upgraded(newImplementation);
    }

    function getImplementation() external view returns (address) {
        return _implementation;
    }

    fallback() external payable {
        address impl = _implementation;
        assembly {
            calldatacopy(0, 0, calldatasize())
            let result := delegatecall(gas(), impl, 0, calldatasize(), 0, 0)
            returndatacopy(0, 0, returndatasize())
            switch result
            case 0 { revert(0, returndatasize()) }
            default { return(0, returndatasize()) }
        }
    }

    receive() external payable {}
}