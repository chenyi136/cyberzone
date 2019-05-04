package com.safecode.javacv.utils;

import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


/**
 * base64转换本地文件
 */
public class Base64Utils {


    public String baseforfile(String base64, String filepath) {
        BASE64Decoder decoder = new BASE64Decoder();
        String baseValue = base64.replaceAll(" ", "+");//前台在用Ajax传base64值的时候会把base64中的+换成空格，所以需要替换回来。            byte[] b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", ""));//去除base64中无用的部分            base64Pic = base64Pic.replace("base64,", "");
        String s = baseValue.replaceAll("data:image/jpeg;base64,", "");
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(s);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
//            int k = (int) (Math.random() * 100);
            String imgpath = filepath + "\\login" + ".jpg";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(imgpath)));
//          OutputStream out = new FileOutputStream(new File(filepath));
            out.write(b);
            out.flush();
            out.close();
            return imgpath;
        } catch (Exception e) {
            System.out.println("生成失败");
            return null;
        }
    }


}
