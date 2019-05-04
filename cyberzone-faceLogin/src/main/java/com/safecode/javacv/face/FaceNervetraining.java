package com.safecode.javacv.face;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chongjie By 2018.10.12
 */
public class FaceNervetraining {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    String filepath = "C:\\Users\\safecode\\Desktop\\fuckme";

    public static void main(String[] args) {
        FaceNervetraining faceNervetraining = new FaceNervetraining();
        faceNervetraining.getfilepath();
    }

    public List<String> getfilepath() {
        List<String> filelist = new ArrayList<>();
        File file = new File(filepath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            Mat srcmat = Imgcodecs.imread(files[i].getPath());
            Mat resize = new Mat();
            Mat trainImage = new Mat();
//            Mat tarindata = new Mat(sample_num_perclass * jpg, image_rows * image_cols, CvType.CV_32FC1);
            Imgproc.resize(srcmat, resize, new Size(480, 480));
            Imgproc.cvtColor(resize, trainImage, Imgproc.COLOR_BGR2HSV);
            for (int row = 0; row < 480; row++) {
                for (int col = 0; col < 480; col++) {
                    double[] doubles = trainImage.get(row, col);
//                    tarindata.put()
                }
            }
        }
        return filelist;
    }
}
