package com.safecode.cyberzone.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;

public class SysLogUtil {
	
	//系统名
	private static String projectName = "cyberzone-detection";
	
	public static Map<String, String> save(HttpServletRequest request, String userName, String remark) throws IOException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> params = WebUtils.getParametersStartingWith(request, null);
	    	JSONObject jsonObj=new JSONObject(params);
	    	map.put("requestObject", jsonObj.toString());
	    	map.put("requestUrl", request.getRequestURL().toString());
	    	map.put("projectName", projectName);
	    	map.put("ip", getIpAdrress(request));
	    	map.put("userName", userName);
	    	map.put("remark", remark);
	    	return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
	}
	
	/**
     * 获取Ip地址
     * @param request
     * @return
     */
    private static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
