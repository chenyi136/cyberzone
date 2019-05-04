package com.safecode.javacv.face;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;
import org.opencv.objdetect.HOGDescriptor;

import java.io.File;
import java.util.Random;

public class TranData {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        carcardTrain();
    }

    public static float[] openCVGetHog(Mat imageMat) {
        System.out.println(imageMat);
        HOGDescriptor hog = new HOGDescriptor(new Size(imageMat.rows(), imageMat.cols()), new Size(16, 16), new Size(8, 8), new Size(8, 8), 9);
        MatOfFloat descriptorsOfMat = new MatOfFloat();
        hog.compute(imageMat, descriptorsOfMat);
        return descriptorsOfMat.toArray();
    }


    private static void carcardTrain() {
        File dir = new File("E:\\face\\1admin");
        File[] files = dir.listFiles();

        int nImgNum = files.length;
        Mat dataMat = null, resMat = null;
        for (int i = 0; i < nImgNum; i++) {
            Mat mat = Imgcodecs.imread(files[i].getAbsolutePath());
            System.out.println("Image:" + mat.size().width + "X" + mat.size().height);
            /*win_size – 检测窗大小。需要和块的大小、步长匹配。
            block_size – 块的大小。需要和细胞大小匹配。目前只支持(16,16)的大小。
            block_stride – 块的步长，必须是细胞大小的整数倍。
            cell_size – 细胞大小。目前只支持(8, 8)的大小。
            nbins – 投票箱的个数。目前只支持每个细胞9个投票箱。
            win_sigma – 高斯平滑窗口参数。
            threshold_L2hys – L2-Hys归一化收缩率。
            gamma_correction – 伽马校正预处理标志，需要或不需要。
            nlevels – 检测窗口的最大数目。*/
            HOGDescriptor hog = new HOGDescriptor(new Size(64, 16), new Size(16, 16), new Size(8, 8), new Size(8, 8), 9);
            System.out.println("HOG:" + hog.get_winSize().width + "X" + hog.get_winSize().height);
            Imgproc.resize(mat, mat, hog.get_winSize());
            MatOfFloat descriptors = new MatOfFloat();
            MatOfPoint matOfPoint = new MatOfPoint();
            hog.compute(mat, descriptors);//调用计算函数开始计
            System.out.println("MatOfFloat:" + descriptors.size().area());
            if (dataMat == null)
                dataMat = new Mat(nImgNum, (int) descriptors.size().area(), CvType.CV_32FC1); //根据输入图片大小进行分配空间
            if (resMat == null)
                resMat = new Mat(nImgNum, 1, CvType.CV_32SC1);//类型矩阵,存储每个样本的类型标志
            int n = 0;
            for (float val : descriptors.toArray()) {
                dataMat.put(i, n, val);
                System.out.print(" " + val);
                n++;
            }

            System.out.println("descriptors.toArray():" + n);
            resMat.put(i, 0, new Random().nextBoolean() ? 1 : -1);
        }

        SVM svm = SVM.create();
        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.RBF);
        svm.setC(10);
        svm.setCoef0(1.0);
        svm.setP(1.0);
        svm.setNu(0.5);
        svm.setTermCriteria(new TermCriteria(TermCriteria.EPS, 1000, 1));
        svm.train(dataMat, Ml.ROW_SAMPLE, resMat);
        svm.save("E:\\lbpcascade_carcard.xml");//此处72行报错
    }

}
