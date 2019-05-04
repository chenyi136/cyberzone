package com.safecode.javacv.face;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.ANN_MLP;
import org.opencv.ml.Ml;
import org.opencv.ml.TrainData;

import java.io.File;
import java.io.IOException;


public class test {

    static ANN_MLP ann_mlp;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        duibi();
//        huidu();
//        yzcode();
//        erzhihua();
//        try {
//            tesseract();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        jiqixunlian();
//        fuck();
//         FaceDetection faceDetection=new FaceDetection();
//         faceDetection.imggary("C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie\\1547204187550.jpg");
        FaceDemo faceDemo = new FaceDemo();
        float face = faceDemo.face("C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie\\1547221666264.jpg", "C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie");
        System.out.println(face);
    }

    static void duibi() {
        String filename1 = "C:\\Users\\safecode\\Desktop\\fuckme\\jj.jpg";
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat img = new Mat(img1.size(), CvType.CV_8UC3);
        System.out.println(img);
    }

    /**
     * 将图像进行灰度转换
     *
     * @return
     */
    public static boolean huidu() {
        //图片地址

        String outPath = "D:/demo2.png";

        //加载lib,这个lib的名称
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //读取图片信息
        Mat image = Imgcodecs.imread("E:\\face\\hhh\\1544685346450.jpg");

        //将rgb灰化处理
        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);

        // 写入到文件
        try {
            return Imgcodecs.imwrite(outPath, image);
        } finally {
            image.release();
            image.release();
        }
    }


    public static void yzcode() {
        Mat mat = Imgcodecs.imread("D:\\2018926135349.jpg");
        int cols = mat.cols();
        int rows = mat.rows();
        System.out.println(rows);
        System.out.println(cols);
        System.out.println(mat.height());
        System.out.println(mat.width());
        //将rgb灰化处理
//        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
//        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,Imgproc.size);
//        Mat mat1 = new Mat();
//        Imgproc.erode(mat, mat1, structuringElement);


        Imgcodecs.imwrite("E:\\face\\fuck.jpg", mat);
    }

    /**
     * 二值化
     */
    public static void erzhihua() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 加载时灰度
        Mat src = Imgcodecs.imread("C:\\Users\\Mr.ChongJie\\Desktop\\yangben\\qqq.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Imgcodecs.imwrite("C:\\Users\\Mr.ChongJie\\Desktop\\yangben\\fff.jpg", src);
        Mat target = new Mat();
        // 二值化处理
        Imgproc.threshold(src, target, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);//灰度图像二值化
        // 保存二值化后图片
        Imgcodecs.imwrite("C:\\Users\\Mr.ChongJie\\Desktop\\yangben\\fuck1.jpg", target);
        Imgproc.adaptiveThreshold(src, target, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 0);
        // 保存二值化后图片
        Imgcodecs.imwrite("C:\\Users\\Mr.ChongJie\\Desktop\\yangben\\fuck2.jpg", target);
    }

    /**
     * opencv ocr识别
     *
     * @throws IOException
     */
    public static void tesseract() throws IOException {
        String resultLine;
        Process p = Runtime.getRuntime().exec("tesserect C:\\Users\\Mr.ChongJie\\Desktop\\yangben\\新建文件夹\\imageCaptcha - 副本 - 副本.jpg ");

    }

    /**
     * 人脸识别机器训练
     */
    public static void jiqixunlian() {
        File file = new File("E:\\face\\1admin");
//        ann_mlp.save("E:\\fuck.xml");
        Mat mat = new Mat(22 * 1, 300 * 300, CvType.CV_32FC1);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            Mat srcmat = Imgcodecs.imread(files[i].getPath());
            System.out.println(srcmat.dump());
            Mat resizeImage = new Mat();
            Mat trainImage = new Mat();
            Imgproc.resize(srcmat, resizeImage, new Size(srcmat.cols(), srcmat.rows()));
            Imgproc.cvtColor(resizeImage, trainImage, Imgproc.COLOR_BGR2GRAY);
            for (int row = 0; row < trainImage.rows(); row++) {
                for (int col = 0; col < trainImage.cols(); col++) {
                    double[] d = trainImage.get(row, col);
                    mat.put(i * 22, row * 300 + col, d);
                }
            }
        }
    }


    // 人工神经网络
    public static Mat MyAnn(Mat trainingData, Mat labels, Mat testData) {
        // train data using aNN
        TrainData td = TrainData.create(trainingData, Ml.ROW_SAMPLE, labels);
        Mat layerSizes = new Mat(1, 4, CvType.CV_32FC1);
        // 含有两个隐含层的网络结构，输入、输出层各两个节点，每个隐含层含两个节点
        layerSizes.put(0, 0, new float[]{2, 2, 2, 2});
        ANN_MLP ann = ANN_MLP.create();
        ann.setLayerSizes(layerSizes);
        ann.setTrainMethod(ANN_MLP.BACKPROP);
        ann.setBackpropWeightScale(0.1);
        ann.setBackpropMomentumScale(0.1);
        ann.setActivationFunction(ANN_MLP.SIGMOID_SYM, 1, 1);
        ann.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 300, 0.0));
        boolean success = ann.train(td.getSamples(), Ml.ROW_SAMPLE, td.getResponses());
        System.out.println("Ann training result: " + success);
        ann.save("D:/bp.xml");//存储模型
        // ann.load("D:/bp.xml");//读取模型

        // 测试数据
        Mat responseMat = new Mat();
        ann.predict(testData, responseMat, 0);
        System.out.println("Ann responseMat:\n" + responseMat.dump());
        for (int i = 0; i < responseMat.size().height; i++) {
            if (responseMat.get(i, 0)[0] + responseMat.get(i, i)[0] >= 1)
                System.out.println("Girl\n");
            if (responseMat.get(i, 0)[0] + responseMat.get(i, i)[0] < 1)
                System.out.println("Boy\n");
        }
        return responseMat;
    }


    public static void fuck() {
        File file = new File("C:\\Users\\Mr.ChongJie\\Desktop\\34zhangchongjie");
        File[] files = file.listFiles();
        for (File file1 : files) {
            file1.delete();
        }
        System.out.println(file.listFiles().length);


    }
}
