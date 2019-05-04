package com.safecode.cyberzone.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by xuq on 2018/10/24.
 * 随机生成32位字符串
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    /*public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }*/

    public static String getRandomForTime() {
        String random = (1000 + (int) (Math.random() * 1000) + "").substring(1);// 随机数防止文件名冲突
        String filepathForTime = new SimpleDateFormat("yyyy-MM-dd-HH:SS:mm").format(new Date()).toString() + "-" + random;
        return filepathForTime;

    }
}
