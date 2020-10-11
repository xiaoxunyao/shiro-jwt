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

    private static final String SIGN = "HCY";

    private static final String ALG = "MD5";

    private static final int HASH = 1024;

    public static String encryption(String password){
        return new SimpleHash(ALG, password, ByteSource.Util.bytes(SIGN), HASH).toString();
    }



}
