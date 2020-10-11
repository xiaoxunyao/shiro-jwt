package com.inet.codebase.utlis;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * ShiroMd5Utlis
 *
 * @author HCY
 * @since 2020/10/11
 */
public class ShiroMd5Utlis {

    /**
     * 加密盐
     */
    private static final String SIGN = "HCY";

    /**
     * 加密算法
     */
    private static final String ALG = "MD5";

    /**
     * 散列次数
     */
    private static final int HASH = 1024;

    /**
     * 创建加密过后的字符串
     * @param password
     * @return
     */
    public static String encryption(String password){
        return new SimpleHash(ALG, password, ByteSource.Util.bytes(SIGN), HASH).toString();
    }



}
