package com.safecode.javacv.face;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author ChongJie
 * @date 2018.10.12
 * face人脸(对比)2
 */
public class FaceDemo {


    /**
     * 人脸对比方法
     *
     * @param imgpath 传来的图片路径
     * @return
     */
    public float face(String imgpath, String filepath) {

        File file = new File(filepath);
        File[] files = file.listFiles();
        //相似度总数
        float k = 0;
        //有效文件个数
        int i = 0;
        for (File file1 : files) {
            if (!file1.getPath().equals(imgpath)) {
                float percent = compare(getData(file1.getPath()),
                        getData(imgpath));
                if (percent == 0) {
                    System.out.println("无法比较");
                    return 0;
                } else {
                    i++;
                    k += percent;
                    System.out.println("两张图片的相似度为：" + percent + "%");
                }
            }

        }
        //相似度
        float xsd = k / i;
//        float percent = compare(getData("C:\\Users\\Mr.ChongJie\\Desktop\\fuckme\\jj3.jpg"),
//                getData("C:\\Users\\Mr.ChongJie\\Desktop\\fuckme\\jj4.jpg"));
//        if (percent == 0) {
//            System.out.println("无法比较");
//        } else {
//            System.out.println("两张图片的相似度为：" + percent + "%");
//        }
        return xsd;
    }

    /**
     * 获得人脸对比数据
     *
     * @param name
     * @return
     */
    public int[] getData(String name) {
        try {
            BufferedImage img = ImageIO.read(new File(name));
            BufferedImage slt = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);
            // ImageIO.write(slt,"jpeg",new File("slt.jpg"));
            int[] data = new int[256];
            for (int x = 0; x < slt.getWidth(); x++) {
                for (int y = 0; y < slt.getHeight(); y++) {
                    int rgb = slt.getRGB(x, y);
                    Color myColor = new Color(rgb);
                    int r = myColor.getRed();
                    int g = myColor.getGreen();
                    int b = myColor.getBlue();
                    data[(r + g + b) / 3]++;
                }
            }
            // data 就是所谓图形学当中的直方图的概念
            return data;
        } catch (Exception exception) {
            System.out.println("有文件没有找到,请检查文件是否存在或路径是否正确");
            return null;
        }
    }

    public static float compare(int[] s, int[] t) {
        try {
            float result = 0F;
            for (int i = 0; i < 256; i++) {
                int abs = Math.abs(s[i] - t[i]);
                int max = Math.max(s[i], t[i]);
                result += (1 - ((float) abs / (max == 0 ? 1 : max)));
            }
            return (result / 256) * 100;
        } catch (Exception exception) {
            return 0;
        }
    }
}

