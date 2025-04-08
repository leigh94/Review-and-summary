package com.leigh.ehernumfunctionalanalysisjava.practice;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * 练习
 * 常用方法
 */
public class ConnectInteraction {
    public static void main(String[] args) throws Exception {
        connectNetwork();
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
            System.out.println(" Connected to Ethereum client: " + clineVersion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void ethereumConnectNetwork() {
        // 连接到链
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        // 加载钱包凭证
        Credentials credentials = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");

        // 合约地址
        String contractAddress = "0x5FbDB2315678afecb367f032d93F642f64180aa3";

        // 加载合约
    }
}
