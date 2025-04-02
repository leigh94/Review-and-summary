package com.leigh.ehernumfunctionalanalysisjava.analyze;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * 模拟以太坊密钥生成器
 */
public class EthereumKeyGenerator {
    public static void main(String[] args) throws Exception{
        // 添加 BouncyCastle 作为安全提供者
        Security.addProvider(new BouncyCastleProvider());

        // 生成  密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","BC");
        keyGen.initialize(new ECGenParameterSpec("secp256k1"));
        KeyPair keyPair = keyGen.generateKeyPair();

        // 获取私钥
        ECPrivateKey privateKey = (ECPrivateKey)keyPair.getPrivate();
        BigInteger privateKeyBigInt =  privateKey.getS();

        // 计算公钥
        BigInteger publicKeyBigInt = Sign.publicKeyFromPrivate(privateKeyBigInt);

        // 创建 ECKeyPair
        ECKeyPair ecKeyPair = new ECKeyPair(privateKeyBigInt, publicKeyBigInt);

        // 计算以太坊地址
        String ethereumAddress = "0x" + Keys.getAddress(ecKeyPair);

        System.out.println("私钥: " + privateKeyBigInt.toString(16));
        System.out.println("公钥: " + publicKeyBigInt.toString(16));
        System.out.println("以太坊地址: " + ethereumAddress);

    }

}
