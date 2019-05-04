package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.service.SysLogService;
import com.safecode.cyberzone.utils.SysLogUtil;


//@CrossOrigin
@RestController
@RequestMapping(value = "sysLog")
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SessionProvider sessionProvider;
	/**
     * 添加系统操作日志
     * @param map
     * @param modelMap
     * @return
     * @throws IOException 
     */
	@Async
    public void add(Map<String, String> map) throws IOException {
    	try {
//    		int userId = Integer.valueOf((String) map.get("userId"));
    		String remark = map.get("remark");
        	String requestObject = map.get("requestObject");
        	String requestUrl = map.get("requestUrl");
        	String ipAddr = map.get("ip");
        	String projectName = map.get("projectName");
    		SysLog sysLog = new SysLog();
    		sysLog.setCreateTime(new Date());
//    		sysLog.setUserId(userId);
    		sysLog.setUserName(map.get("userName"));
    		sysLog.setRemark(remark);
    		sysLog.setProjectName(projectName);
    		sysLog.setRequestObject(requestObject);
    		sysLog.setRequestUrl(requestUrl);
    		sysLog.setIpAddr(ipAddr);
    		sysLogService.insert(sysLog);
    	}catch (Exception e) {
    		e.printStackTrace();
			throw e; 
		}
		return;
    }
	
	@RequestMapping(value = "/addLog", method = RequestMethod.POST)
    public void addLog(HttpServletRequest request,String projectName) 
    		throws Exception {
		SysLog log = new SysLog();
    	log.setProjectName(projectName);
//    	String account = sessionProvider.getAttributeForAccount(TicketUtil.getJSESSIONID(request, null));
    	String userAccount = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null)).getAccount();
//    	log.setUserName(account);
    	String project = "";
    	if(!projectName.equals("") && projectName != null) {
    		if(projectName.equals("cyberzone-common")) {
    			project = "靶场支撑平台";
    		} else if(projectName.equals("cyberzone-resource")) {
    			project = "资源库";
    		} else if (projectName.equals("cyberzone-detection")) {
    			project = "测评与服务平台";
			}
    	}else {
    		return;
    	}
    	
    	Map<String, String> map = SysLogUtil.save(request,userAccount,"进入:"+project);
    	this.add(map);
    }
}
