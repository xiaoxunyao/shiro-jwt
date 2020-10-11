package com.inet.codebase.utlis;

import java.util.UUID;

/**
 * UUID得工具类
 * @author HCY
 * @since 2020-10-10
 */
public class UUIDUtils {
    /**
     * 随机生成id
     * @return
     */
    public static String getId(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 生成随机码
     * @return
     */
    public static String getCode(){
        return getId();
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}
