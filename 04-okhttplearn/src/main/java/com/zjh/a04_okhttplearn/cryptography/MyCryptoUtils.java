package com.zjh.a04_okhttplearn.cryptography;

import java.security.SecureRandom;

public class MyCryptoUtils{
    /**
     * 不要使用Random类来获取随机数。
     * 在使用SecureRandom时候，不要设置种子。使用以下函数设置种子都是有风险的：
     */
    public static void secureRandom(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] out = new byte[16];
        secureRandom.nextBytes(out);

        System.out.println(new String(out));

    }
}
