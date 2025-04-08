package com.leigh.ehernumfunctionalanalysisjava.practice;

import com.leigh.ehernumfunctionalanalysisjava.contracts.LocalTestContract;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 练习
 * 常用方法
 */
public class ConnectInteraction {

    // 合约地址
    final static String LOCAL_TEST_CONTRACT_ADDRESS = "0x5FbDB2315678afecb367f032d93F642f64180aa3";

    public static void main(String[] args) throws Exception {
//        //  连接以太坊网络
//        connectNetwork();
//
//        // 连接本地合约 调用对应函数
//        ethereumConnectNetwork();
//
//        // 部署合约
//        deployEthereumContract();
//
//        // 查询账户余额
//        getBalance();

        /* 发送以太币交易
            Account #0: 0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266 (10000 ETH)
            Private Key: 0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80

            Account #1: 0x70997970C51812dc3A010C7d01b50e0d17dc79C8 (10000 ETH)
            Private Key: 0x59c6995e998f97a5a0044966f0945389dc9e86dae88c7a8412f4603b6b78690d
         */
//        getBalance("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266");
//        getBalance("0x70997970C51812dc3A010C7d01b50e0d17dc79C8");
//        sendEther();
//        getBalance("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266");
//        getBalance("0x70997970C51812dc3A010C7d01b50e0d17dc79C8");

        //监听 MessageChanged(string) 事件
        eventListenerExample();
    }

    /**
     * 连接以太坊网络 Web3j.build
     * 是使用Web3j 实例来实现 连接
     */
    public static void connectNetwork() {
        try {
            // 连接以太坊节点
            Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

            // 获取客户端版本信息
            String clineVersion =  web3j.web3ClientVersion().send().getWeb3ClientVersion();
            System.out.println("connectNetwork Connected to Ethereum client: " + clineVersion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 连接本地合约 调用对应函数
     * @throws Exception
     */
    public static void ethereumConnectNetwork() throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        // 加载钱包凭证
        Credentials credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");



        // 加载合约
        LocalTestContract localTestContract = LocalTestContract.load(LOCAL_TEST_CONTRACT_ADDRESS,web3j,credentials,new DefaultGasProvider());

        localTestContract.setMessage("Hello World").send();

        String message = localTestContract.getMessage().send();
        System.out.println("ethereumConnectNetwork Contract message: " + message);
    }

    /**
     * 部署合约
     * @throws Exception
     */
    public static void deployEthereumContract() throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        // 加载钱包
        Credentials credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");

        // 部署合约
        ContractGasProvider gasProvider = new DefaultGasProvider();

        LocalTestContract localTestContract = LocalTestContract.deploy(web3j, credentials, gasProvider).send();
        String contractAddress = localTestContract.getContractAddress();
        System.out.println("deployEthereumContract Contract address: " + contractAddress);
    }

    /**
     * 查询账户余额
     * @throws Exception
     */
    public static void getBalance() throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        String address = "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266";
//        DefaultBlockParameterName
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigInteger balanceInWei = ethGetBalance.getBalance();
        System.out.println("getBalance balanceInWei: " + balanceInWei);
    }

    /**
     * 查询账户余额
     * @param address  地址
     * @throws Exception
     */
    public static void getBalance(String address) throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

//        DefaultBlockParameterName
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigInteger balanceInWei = ethGetBalance.getBalance();
        System.out.println(address + " getBalance balanceInWei: " + balanceInWei);
    }

    /**
     * 发送以太币
     * @throws Exception
     */
    public static void sendEther() throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        // 加载钱包
        Credentials credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");

        String recipientAddress = "0x70997970C51812dc3A010C7d01b50e0d17dc79C8";

        TransactionReceipt receipt =  Transfer.sendFunds(web3j,credentials,recipientAddress, BigDecimal.valueOf(0.01), Convert.Unit.ETHER).send();

        System.out.println("sendEther Transaction successful, hash: : " + receipt.getTransactionHash());
    }


    /**
     * 监听 MessageChanged(string) 事件
     * @throws Exception
     */
    public static void eventListenerExample() throws Exception {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        // 加载钱包
        Credentials credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");

        LocalTestContract localTestContract = LocalTestContract.load(LOCAL_TEST_CONTRACT_ADDRESS,web3j,credentials,new DefaultGasProvider());

        localTestContract.messageChangedEventFlowable(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    System.out.println("监听到事件！新消息: " + event.newMessage);
                });

        // 保持程序运行
        System.out.println("监听中，按 Comm+F2 退出");
        Thread.sleep(Long.MAX_VALUE);

        // 取消订阅（实际一般不会到这里）
        // subscription.dispose();

    }

}
