package com.safecode.cyberzone.utils;

import java.util.UUID;

/**
 * Created by xuq on 2018/10/24.
 * 随机生成32位字符串
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }
}
