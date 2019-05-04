package com.safecode.cyberzone.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.utils.DateUtil;
import com.safecode.cyberzone.base.utils.DateUtil.DATE_PATTERN;

public class FileUtil {

	public static final String UPLOADPATH = "/home/hmj/saveFile";
//	public static final String UPLOADPATH = "C:/Users/huocf/Desktop/img";

	/**
	 * 生成新文件名称
	 * 
	 * @param originalFilename
	 *            		原文件名
	 * @return
	 */
	public static String createFileName(String originalFilename) {
		String random = (1000000 + (int) (Math.random() * 1000000) + "").substring(1);// 随机数防止文件名冲突
		// 新生成的文件名称
		String fileName = DateUtil.format(new Date(), DATE_PATTERN.YYYYMMDDHHMMSSSSS) + random
				+ originalFilename.substring(originalFilename.lastIndexOf("."));
		return fileName;
	}

	/**
	 * 文件复制
	 * 
	 * @param fromUrl
	 *            源文件路径
	 * @param toUrl
	 *            需要复制到的路径
	 */
	public static void copyFile(String fromUrl, String toUrl) {
		System.out.println("老文件路径：" + fromUrl + "----" + "新文件路径：" + toUrl);

		File sFile = new File(fromUrl);
		File tFile = new File(toUrl);
		
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		
		try {
			fi = new FileInputStream(sFile);
			fo = new FileOutputStream(tFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("复制成功！");
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 *            MultipartFile对象
	 * @param uploadPath
	 *            上传存储路径（磁盘路径）
	 * @throws IOException 
	 */
	public static FileInfo saveFile(MultipartFile file, String uploadPath) throws IOException {
		FileInfo fileInfo = new FileInfo();
		try {
			String fileName = file.getOriginalFilename();// 原文件名(有后缀)
			String realName = createFileName(fileName);//新文件名
			
			String path = uploadPath + "/" + realName;//存储路径
			File targetFile = new File(path);
			
			//创建文件夹
	        if(!targetFile.getParentFile().exists()){  
	            targetFile.getParentFile().mkdirs();  
	        }
			
			file.transferTo(targetFile);  

			fileInfo.setName(fileName.substring(0, fileName.indexOf(".")));
			fileInfo.setFileName(fileName);
			fileInfo.setRealName(realName);
			fileInfo.setFilePath(path);
			fileInfo.setFileSuffix(fileName.substring(fileName.lastIndexOf(".") + 1));
			fileInfo.setFileSize(file.getSize());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return fileInfo;
	}

	/**
	 * 将远程服务器文件下载到本地服务器
	 * 
	 * @param theURL
	 *            远程服务器文件路径（http路径+文件名）
	 * @param downloadPath
	 *            本地存储路径(磁盘路径+文件名)
	 * @throws IOException
	 */
	public static void downloadFile(URL theURL, String downloadPath) throws IOException {
		try {
			File dirFile = new File(downloadPath);
			if (!dirFile.exists()) {// 文件路径不存在时，自动创建目录
				dirFile.mkdir();
			}

			URLConnection connection = theURL.openConnection();
			InputStream in = connection.getInputStream();
			FileOutputStream os = new FileOutputStream(downloadPath);
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				os.write(buffer, 0, read);
			}
			os.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr	远程服务器文件路径（http路径+文件名）
	 * @param fileName	原文件名
	 * @param downloadPath	本地存储路径（磁盘路径，无文件名）
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String downloadPath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = FileCopyUtils.copyToByteArray(inputStream);

		// 本地存储路径
		File saveDir = new File(downloadPath);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		// 通过文件输出流，写出文件
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);

		// 关流
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		System.out.println("info:" + url + " download success");
	}

	/**
	 * 删除指定的目录或文件
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) {
			return flag; // 不存在返回 false
		} else {
			// 判断是否为文件
			if (file.isFile()) {
				return deleteFile(sPath); // 调用删除文件方法
			} else {
				return deleteDirectory(sPath); // 调用删除目录方法
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名（磁盘路径+文件名）
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			if(file.delete()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹） 以及目录下的 文件和子目录
	 * 
	 * @param sPath
	 *            被删除目录的文件路径（磁盘路径，无文件名）
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}

		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}

		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}

		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * 文件下载
     * @param request
     * @param response
     * @param url 下载路径
     * @param fileName  文件名（带后缀）
     * @return
     * @throws IOException 
     */
	public static void downLoadFile(HttpServletRequest req, HttpServletResponse resp, String url, String fileName) throws IOException {
		//获取文件名，此处看个人如何设计的
    	fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");

    	String path = url;
    	File file = new File(path);
    	//如果文件不存在
    	if(!file.exists()){
    	    return;
    	}
    	//设置响应头，控制浏览器下载该文件
    	resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    	//读取要下载的文件，保存到文件输入流
    	FileInputStream in = new FileInputStream(path);
    	//创建输出流
    	OutputStream out = resp.getOutputStream();
    	//缓存区
    	byte buffer[] = new byte[1024];
    	int len = 0;
    	//循环将输入流中的内容读取到缓冲区中
    	while((len = in.read(buffer)) > 0){
    	    out.write(buffer, 0, len);
    	}
    	//关闭
    	in.close();
    	out.close();
	}
	/**
	 * 文件在线预览
	 * @param req
	 * @param resp
	 * @param url
	 * @throws IOException
	 */
	public static void onlinePreview(HttpServletRequest req, HttpServletResponse resp, String filePath) throws IOException {

    	File file = new File(filePath);
    	//如果文件不存在
    	if(!file.exists()){
    	    return;
    	}
    	//读取要下载的文件，保存到文件输入流
    	FileInputStream in = new FileInputStream(filePath);
    	//创建输出流
    	OutputStream out = resp.getOutputStream();
    	//缓存区
    	byte buffer[] = new byte[1024];
    	int len = 0;
    	//循环将输入流中的内容读取到缓冲区中
    	while((len = in.read(buffer)) > 0){
    	    out.write(buffer, 0, len);
    	}
    	//关闭
    	in.close();
    	out.close();
	}

}
