package com.safecode.cyberzone.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.base.support.Code;
import com.safecode.cyberzone.po.VulnerabilityCnvd;
import com.safecode.cyberzone.po.VulnerabilityNvd;
import com.safecode.cyberzone.service.SyncService;
import com.safecode.cyberzone.utils.FileUtil;

@Component // 使spring管理
@EnableScheduling // 定时任务注解
@RestController
@RequestMapping(value = "sync")
@PropertySource(value = {"classpath:xmlurl.properties"} , ignoreResourceNotFound = true)
public class SyncController {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Value("${offline.sync.url}")
	private String offlineSync;
	
	@Value("${offline.sync.filename}")
	private String offlineFileName;
	
	@Value("${nvdA}")
	private String nvdA;
	
	@Value("${nvdB}")
	private String nvdB;
	
	@Value("${cnvdA}")
	private String cnvdA;
	
	@Value("${cnvdB}")
	private String cnvdB;
	
	@Autowired
	private SyncService syncService;

	/**
	 * 在线同步
	 * @param nvdUpdateTime nvd更新时间
	 * @param cnvdUpdateTime cnvd更新时间 
	 * @param orgCode 组织代码
	 * @return
	 */
	@RequestMapping(value = "/onlineSync", method = RequestMethod.POST)
	public Object onlineSync(String nvdUpdateTime, String cnvdUpdateTime, String orgCode) {
		
		logger.warn("组织编码："+orgCode+"；请求参数：nvdUpdateTime="+nvdUpdateTime+"；请求参数：cnvdUpdateTime="+cnvdUpdateTime);
		
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		
		List<VulnerabilityNvd> nvdList = syncService.onlineSyncNvd(nvdUpdateTime);
		List<VulnerabilityCnvd> cnvdList = syncService.onlineSyncCnvd(cnvdUpdateTime);
		
		responseMap.put("nvdList", nvdList);
		responseMap.put("cnvdList", cnvdList);
		
		return new ResponseData<Map<Object, Object>>(Code.OK.value(), null, responseMap);
	}

	 /**
	  * 离线同步
	  * @param req
	  * @param resp
	  * @throws IOException
	  */
    @RequestMapping(value = "/offlineSync", method = RequestMethod.GET)
    public void downLoad(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	FileUtil.downLoadFile(req, resp, offlineSync, offlineFileName);
	 }
    
//	@Async
	@Scheduled(cron = "0 0/20 * * * ? ")
	public void importNvd() throws IOException, DocumentException {
		// 从A文件夹移动到B文件夹
		cutFolder(nvdA, nvdB);
		// 解析NVD XML
		syncService.importVulnerabilityXml(1, nvdB);
	}

//	@Async
	@Scheduled(cron = "0 0/20 * * * ? ")
	public void importCnvd() throws IOException, DocumentException {
		// 从A文件夹移动到B文件夹
		cutFolder(cnvdA, cnvdB);
		// 解析VNVD XML
		syncService.importVulnerabilityXml(2, cnvdB);
	}

	private void cutFolder(String fromPath, String toPath) {
		File dir = new File(fromPath);
		File[] files = dir.listFiles();

		// 目标文件夹不存在 则创建
		if (!(new File(toPath)).exists()) {
			(new File(toPath)).mkdir();
		}

		for (File currentFile : files) {
			String fileName = currentFile.getName();
			String pathName = currentFile.getPath();
			if (currentFile.isFile() && fileName.substring(fileName.lastIndexOf(".") + 1).equals("xml")) {
				try {
					// 从fromPath复制到toPath
					FileUtil.copyFile(pathName, toPath + dir.separator + fileName);
					// 复制一个删除一个
					FileUtil.deleteFile(pathName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // if end
		} // for end
	}

}