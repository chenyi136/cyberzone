package com.safecode.javacv.face;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChongJie
 * @date 2018.10.12
 * 人脸识别1
 * 识别流程：人脸检测-对图片中的人脸进行截取-两张图片像素对比
 */
public class Facecontrast {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie");
        String filename1 = "C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie\\login.jpg";
//        int i = compareHistogram(filename1, "C:\\Users\\Mr.ChongJie\\Desktop\\fuckme\\jj4.jpg");
//        System.out.println("识别结果为" + i + "  文件为:" + file.getName());
        double sum = 0;
        File[] tempList = file.listFiles();
        int num = tempList.length;
        for (File file1 : tempList) {
            double i = compareHistogram(filename1, "C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie\\" + file1.getName());
            if (i != 0) {
                sum++;
            }
            System.out.println();
            System.out.println("识别结果为" + i + "  文件为:" + file1.getName());
        }
        System.out.println(num + "总文件个数");
        System.out.println("相似文件个数" + sum);
        System.out.println("相似度为:" + (sum / num) * 100 + "%");
    }


    /**
     * 人脸识别方法
     *
     * @param filename1 对比图片1的路径
     * @param filename2 对比图片2的路径dd
     * @return 返回int类型 1:认为一致 0：不一致
     */
    public static int compareHistogram(String filename1, String filename2) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int retVal = 0;

        long startTime = System.currentTimeMillis();

//        System.load("C:\\opencv\\build\\java\\x64\\opencv_java343.dll");
// Load images to compare
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.CV_LOAD_IMAGE_COLOR);

        Mat hsvImg1 = new Mat();
        Mat hsvImg2 = new Mat();

// Convert to HSV
        Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

        // Set configuration for calchist()
        List<Mat> listImg1 = new ArrayList<Mat>();
        List<Mat> listImg2 = new ArrayList<Mat>();

        listImg1.add(hsvImg1);
        listImg2.add(hsvImg2);

        MatOfFloat ranges = new MatOfFloat(0, 255);
        MatOfInt histSize = new MatOfInt(50);
        MatOfInt channels = new MatOfInt(0);


        // Histograms
        Mat histImg1 = new Mat();
        Mat histImg2 = new Mat();

        // Calculate the histogram for the HSV imgaes
        Imgproc.calcHist(listImg1, channels, new Mat(), histImg1, histSize, ranges);
        Imgproc.calcHist(listImg2, channels, new Mat(), histImg2, histSize, ranges);

        Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX, -1, new Mat());


        // Apply the histogram comparison methods
        // 0 - correlation: the higher the metric, the more accurate the match "> 0.9"
        // 1 - chi-square: the lower the metric, the more accurate the match "< 0.1"
        // 2 - intersection: the higher the metric, the more accurate the match "> 1.5"
        // 3 - bhattacharyya: the lower the metric, the more accurate the match  "< 0.3"
        //应用直方图比较方法
        // 0 - 相关性：度量越高，匹配越准确“> 0.9”
        // 1 - 卡方：指标越低，匹配“<0.1”越准确
        // 2 - 交点：指标越高，匹配越准确“> 1.5”
        // 3 - bhattacharyya：指标越低，匹配越准确“<0.3”


        double result0, result1, result2, result3;
        result0 = Imgproc.compareHist(histImg1, histImg2, 0);
        result1 = Imgproc.compareHist(histImg1, histImg2, 1);
        result2 = Imgproc.compareHist(histImg1, histImg2, 2);
        result3 = Imgproc.compareHist(histImg1, histImg2, 3);

        //输出各个比较方法的值
        //        System.out.println("Method [0] " + result0);
        //        System.out.println("Method [1] " + result1);
        //        System.out.println("Method [2] " + result2);
        //        System.out.println("Method [3] " + result3);

        // If the count that it is satisfied with the condition is over 3, two images is same.
        //最起码有三个方法的数值达标才可以判定为人脸一致
        int count = 0;
        if (result0 > 0.9) count++;
        if (result1 < 0.1) count++;
        if (result2 > 1.5) count++;
        if (result3 < 0.3) count++;
        System.out.println(count);
        if (count >= 3) retVal = 1;


        //输出运行时间
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime=" + estimatedTime + "ms");

        img1.release();
        img2.release();
        histImg1.release();
        histImg2.release();
        return retVal;
    }

}