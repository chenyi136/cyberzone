package com.safecode.javacv.service.impl;

import com.safecode.javacv.service.FaceLoginService;
import com.safecode.javacv.face.FaceDemo;
import com.safecode.javacv.face.FaceDetection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@Service
@PropertySource("classpath:face.properties")
public class FaceLoginServiceImpl implements FaceLoginService {

    //人脸识别率
    @Value("${face}")
    private int num;


    /**
     * 根据路径对人脸进行对比
     *
     * @param imgpath 当前生成的对比文件的路径
     * @param filpath 需要对比的文件目录
     * @return
     */
    @Override
    @Async
    public ListenableFuture<Boolean> faceissuccess(String imgpath, String filpath) {
        FaceDetection faceDetection = new FaceDetection();
        try {
            boolean b = faceDetection.detectFace(imgpath, imgpath);
            if (b) {
                FaceDemo faceDemo = new FaceDemo();
                float face = faceDemo.face(imgpath, filpath);


//                File file = new File(filpath);
//                double sum = 0;
//                File[] tempList = file.listFiles();
//                int num = tempList.length;
//                for (File file1 : tempList) {
//                    double i = Facecontrast.compareHistogram(imgpath, file1.getPath());
//                    if (i != 0) {
//                        sum++;
//                    }
//                    System.out.println("识别结果为" + i + "  文件为:" + file1.getName());
//                }
//                System.out.println(num + "总文件个数");
//                System.out.println("相似文件个数" + sum);
//                double face = (sum / num) * 100;
                System.out.println("相似度为:" + face);
                if (face >= num) {
                    return new AsyncResult<>(true);
                } else {
                    return new AsyncResult<>(false);
                }
            } else {
                return new AsyncResult<>(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<>(false);
        } finally {
            File file = new File(imgpath);
            file.delete();
        }
    }


    /**
     * 人脸注册方法
     *
     * @param base64code
     * @return
     */
    @Override
    @Async
    public ListenableFuture<String> register(String base64code, String filepath, String useraccount, int userid) {
        BASE64Decoder decoder = new BASE64Decoder();
        String baseValue = base64code.replaceAll(" ", "+");//前台在用Ajax传base64值的时候会把base64中的+换成空格，所以需要替换回来。            byte[] b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", ""));//去除base64中无用的部分            base64Pic = base64Pic.replace("base64,", "");
        String s = baseValue.replaceAll("data:image/jpeg;base64,", "");
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(s);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            File file = new File(filepath + "/" + userid + useraccount);
            //判断文件夹是否存在,如果不存在则创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

//            int k = (int) (Math.random() * 100);
            Date date = new Date();
            String imgpath = file.getPath() + "/" + date.getTime() + ".jpg";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(imgpath)));
            out.write(b);
            out.flush();
            out.close();
            System.out.println(imgpath);
            FaceDetection faceDetection = new FaceDetection();
            boolean b1 = faceDetection.detectFace(imgpath, imgpath);
            if (b1) {
                return new AsyncResult<>(file.getPath() + "/");
            } else {
                //如果人脸取样有问题直接删除该目录避免影响后续注册
                File parentFile = new File(imgpath).getParentFile();
                File[] files = parentFile.listFiles();
                for (File delfile : files) {
                    delfile.delete();
                }
                parentFile.delete();
                return new AsyncResult<>("faild");
            }
        } catch (Exception e) {
            return new AsyncResult<>("faild");
        }

    }

}
