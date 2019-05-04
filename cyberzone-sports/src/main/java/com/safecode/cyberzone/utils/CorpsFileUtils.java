package com.safecode.cyberzone.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;

/**
 * 上传文件工具类
 *
 * @author zhangchongjie By 2018.10.25
 */
public class CorpsFileUtils {
    /**
     * 上传战队logo图片到linux服务器
     *
     * @param logophoto
     * @return
     */
    public String headphotoupdownload(MultipartFile logophoto) throws IOException {
        String originalFilename = logophoto.getOriginalFilename();
        System.out.println(originalFilename);
        int y, m, d, h, mi, s;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = (cal.get(Calendar.MONTH) + 1);
        d = cal.get(Calendar.DATE);
        h = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);
        s = cal.get(Calendar.SECOND);
        System.out.println("现在时刻是" + y + "年" + m + "月" + d + "日" + h + "时" + mi + "分" + s + "秒");
        String filepath = "/home/hmj/saveFile/corpsPhoto/" + y + m + d + h + mi + s + ".jpg";
        File photofile = new File(filepath);
        if (!photofile.getParentFile().exists()) {
            photofile.getParentFile().mkdirs();//创建文件夹
        }
//        filepath = "D://sports//" + y + m + d + h + mi + s + ".jpg";
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(photofile));
        try {
            byte[] bytes = logophoto.getBytes();
            bufferedOutputStream.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }

        return filepath;
    }

    /**
     * 对战队修改头像后之前文件的清除
     *
     * @param FilePath
     * @return boolean 是否删除成功
     */
    public boolean deleteUpLoadFile(String FilePath) {
        File file = new File(FilePath);
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String base64photo(String filepath) throws IOException {
        InputStream inputStream = new FileInputStream(new File(filepath));
        byte[] b = new byte[inputStream.available()];
        inputStream.read(b);
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        String encode = ("data:image/jpeg;base64," + encoder.encode(b));
        return encode;
    }


    @RequestMapping("showImage")
    public void showImageByType(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {
        InputStream inputStream = null;
        OutputStream writer = null;
        try {
            inputStream = new FileInputStream(new File(path));
            writer = response.getOutputStream();

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                writer.write(buf, 0, len); //写
            }
            inputStream.close();
        } catch (Exception e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
