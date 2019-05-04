package com.safecode.cyberzone.utils;

//import com.github.junrar.Archive;
//import com.github.junrar.rarfile.FileHeader;

import com.safecode.cyberzone.controller.MalwareDetectionController;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

public class CompressedFileUtils {

    private static Logger log = LoggerFactory.getLogger(CompressedFileUtils.class);

    public static String unZipFiles(String zipFileStr) throws IOException {


        String descDir = zipFileStr.substring(0, zipFileStr.lastIndexOf("/"));
        File pathFile = new File(descDir);

        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(new File(zipFileStr), "GBK");

        for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {

            ZipEntry entry = (ZipEntry) entries.nextElement();

            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");

            //判断路径是否存在,不存在则创建文件路径
            String fileP = outPath.substring(0, outPath.lastIndexOf('/'));
            File file = new File(fileP);

            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            //输出文件路径信息
            //System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);

            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        log.info("解压完毕，目标目录：" + descDir);
        System.out.println("******************解压完毕********************");
        return descDir;
    }

    /**
     * 解压gz文件
     *
     * @param inFileName
     */

    public static String unGzFiles(String archive) throws IOException {

        File file = new File(archive);

        BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(file));

        String fileName =
                file.getName().substring(0, file.getName().lastIndexOf("."));

        String finalName = file.getParent() + File.separator + fileName;

        BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(finalName));

        GzipCompressorInputStream gcis =
                new GzipCompressorInputStream(bis);

        byte[] buffer = new byte[1024];
        int read = -1;
        while ((read = gcis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        gcis.close();
        bos.close();
        if (finalName.endsWith(".tar")) {
            String path = unTarFiles(finalName);
            return path;
        }
        return fileName;

    }

    /**
     * 解压tar文件
     */

    public static String unTarFiles(String finalName) throws IOException {

        File file = new File(finalName);
        String parentPath = file.getParent();
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        String dirPath = parentPath + "/" + fileName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        TarArchiveInputStream tais =
                new TarArchiveInputStream(new FileInputStream(file));

        TarArchiveEntry tarArchiveEntry = null;

        while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
            String name = tarArchiveEntry.getName();
            String outPath = (dirPath + "/" + name).replaceAll("\\*", "/");

            //判断路径是否存在,不存在则创建文件路径
            String fileP = outPath.substring(0, outPath.lastIndexOf('/'));
            File fi = new File(fileP);

            if (!fi.exists()) {
                fi.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(outPath));

            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = tais.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.close();
        }
        tais.close();
        file.delete();//删除tar文件
        return dirPath;
    }

    /**
     * 解压rar文件，只适合解压rar4.0以下的版本
     *
     * @param srcRarPath
     * @param dstDirectoryPath
     */
   /* public static void unRarFiles(String  srcRarPath, String dstDirectoryPath){
        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
            System.out.println("非rar文件！");
            return;
        }
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        File fol=null,out=null;
        Archive a = null;
        try {
            a = new Archive(new File(srcRarPath));

            if (a != null){
                a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null){
                    if (fh.isDirectory()) { // 文件夹
                        // 如果是中文路径，调用getFileNameW()方法，否则调用getFileNameString()方法，还可以使用if(fh.isUnicode())
                   if(existZH(fh.getFileNameW())){
                       fol = new File(dstDirectoryPath + File.separator + fh.getFileNameW());
                   }else{
                       fol = new File(dstDirectoryPath + File.separator + fh.getFileNameString());
                   }
                    fol.mkdirs();
                    } else { // 文件
                        if(existZH(fh.getFileNameW())){
                            out = new File(dstDirectoryPath + File.separator + fh.getFileNameW().trim());
                        }else{
                            out = new File(dstDirectoryPath + File.separator
                                    + fh.getFileNameString().trim());
                        }

                        //System.out.println(out.getAbsolutePath());
                        try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()){// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*

     * 判断是否是中文

     */
    public static boolean existZH(String str) {

        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return true;
        }
        return false;
    }
}
