package com.safecode.javacv.face;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

/**
 * @author ChongJie
 * @version v1.00
 * 人脸检测并裁剪
 * 目前只能裁剪正脸
 */
public class FaceDetection {

    static {
        // 导入opencv的库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
//        String imgpath = "C:\\Users\\Mr.ChongJie\\Desktop\\fuckme\\48.jpg";
//        try {
//            detectFace(imgpath, "C:\\Users\\Mr.ChongJie\\Desktop\\fuckme\\jj4.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String imgpath = "E:\\face\\34zhangchongjie";
        File file = new File(imgpath);
        File[] files = file.listFiles();
        FaceDetection faceDetection = new FaceDetection();
        for (File file1 : files) {
            System.out.println(file1.getName());
            try {
                faceDetection.detectFace(file1.getPath(), file1.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public boolean detectFace(String imagePath, String outFile) throws Exception {
        System.out.println("\nRunning DetectFaceDemo");
        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
//        CascadeClassifier faceDetector = new CascadeClassifier(
//                "C:\\opencv\\sources\\data\\haarcascades_cuda\\haarcascade_frontalface_alt.xml");
        System.out.println("===========操作系统是:" + System.getProperties().getProperty("os.name"));
        if ("Linux".equals(System.getProperties().getProperty("os.name"))) {

        }
        CascadeClassifier faceDetector;
        if ("Linux".equals(System.getProperties().getProperty("os.name"))) {
            faceDetector = new CascadeClassifier(
                    "/usr/local/share/OpenCV/haarcascades/haarcascade_frontalface_alt2.xml");
        } else {
            faceDetector = new CascadeClassifier(
                    "C:\\opencv\\sources\\data\\haarcascades_cuda\\haarcascade_frontalface_alt.xml");
        }
        //读取jpg文件
        Mat image = Imgcodecs.imread(imagePath);
        Mat gray = new Mat();
        //将rgb灰化处理
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // 写入到文件
        try {
            Imgcodecs.imwrite(imagePath, gray);
        } finally {
            image.release();
            image.release();
        }

        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(gray, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));
        Rect[] rects = faceDetections.toArray();
        if (rects != null && rects.length > 1) {
//            throw new RuntimeException("超过一个脸");
            new File(imagePath).delete();
            return false;
        }
        if (rects != null && rects.length > 0) {
            // 在每一个识别出来的人脸周围画出一个方框
            Rect rect = rects[0];
            Imgproc.rectangle(gray, new Point(rect.x - 2, rect.y - 2), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            Mat sub = gray.submat(rect);
            Mat mat = new Mat();
            Size size = new Size(300, 300);
            Imgproc.resize(sub, mat, size);//将人脸进行截图并保存

            try {
                return Imgcodecs.imwrite(outFile, mat);
            } finally {
                mat.release();
                sub.release();
                gray.release();
            }
        } else {
            gray.release();
            System.out.println("没有检测到脸");
            return false;
        }

// 将结果保存到文件
//        String filename = "C:\\Users\\Administrator\\Desktop\\opencv\\faceDetection.png";
//        System.out.println(String.format("Writing %s", filename));
//        Highgui.imwrite(filename, image);
    }


    /**
     * 将图片进行灰度处理
     *
     * @param imgpath 需要处理的图片路径
     */
    public void imggary(String imgpath) {
        Mat imread = Imgcodecs.imread(imgpath);
        Mat image = new Mat();
        //将rgb灰化处理
        Imgproc.cvtColor(imread, image, Imgproc.COLOR_BGR2GRAY);

        // 写入到文件
        try {
            Imgcodecs.imwrite(imgpath, image);
        } finally {
            image.release();
            image.release();
        }

    }

}
