package com.safecode.javacv.face;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@PropertySource("classpath:face.properties")
public class file {

    @Value("${face}")
    private static String face;

    public static void main(String[] args) {
        FaceDemo FaceDemo = new FaceDemo();
        File file = new File("C:\\Users\\safecode\\Desktop\\fuckme");
        File[] tempList = file.listFiles();
        for (File file1 : tempList) {
            float percent = FaceDemo.compare(FaceDemo.getData("C:\\Users\\safecode\\Desktop\\fuckme\\1.jpg"),
                    FaceDemo.getData("C:\\Users\\safecode\\Desktop\\fuckme\\" + file1.getName()));
            if (percent == 0) {
                System.out.println("无法比较");
            } else {
                System.out.println("两张图片的相似度为：" + percent + "%");
            }
        }
    }
}