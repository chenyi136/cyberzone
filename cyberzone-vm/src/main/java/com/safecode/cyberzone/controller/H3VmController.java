package com.safecode.cyberzone.controller;

import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope; 
import org.apache.http.auth.UsernamePasswordCredentials; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.pojo.WebserviceResultBean;
import com.safecode.cyberzone.utils.SoapUtil; 

@CrossOrigin
@RestController
@RequestMapping(value = "h3vm")
public class H3VmController extends BaseController {
	public static DefaultHttpClient client; 
	
	@Value("${h3.host.url}") private String hostUrl;//獲取所有主機接口 
	@Value("${h3.vmListByHost.url}") private String vmListByHostUrl;//根據主機id查詢其下所有虛擬機
	@Value("${h3.vnc.url}") private String vncUrl;
	
	/* 以实例方式实现 H3C CAS 3.0 云计算管理平台认证 */  
	public static DefaultHttpClient newInstance() {   
		if (client == null) {    
			client = new DefaultHttpClient();    
			client.getCredentialsProvider().setCredentials(      
					new AuthScope("192.168.100.1", 8080, "VMC RESTful Web Services"),      
					new UsernamePasswordCredentials("admin", "admin")
			);   
		}   return client;  
	}
	
	/* 获取所有主机列表 */  
	@RequestMapping(value = "/host", method = RequestMethod.GET)
	public Object getHost(HttpServletRequest request, ModelMap modelMap) throws Exception {
		DefaultHttpClient client = newInstance();   
		HttpGet get = new HttpGet(hostUrl);   
		get.addHeader("accept", "application/xml");   
		HttpResponse response = client.execute(get);   
		String deptXML = String.valueOf(EntityUtils.toString(response.getEntity()));
		Map map = new SoapUtil().parse(deptXML);
		return setSuccessModelMap(modelMap,map);
	}
	
	/* 根据主机id获取其下所有虚拟机列表 */  
	@RequestMapping(value = "/vmListByHostUrl", method = RequestMethod.GET)
	public Object getVmListByHost(HttpServletRequest request, ModelMap modelMap, long hostId) throws Exception {
		DefaultHttpClient client = newInstance();   
		HttpGet get = new HttpGet(vmListByHostUrl + "?hostId=" + hostId);   
		get.addHeader("accept", "application/xml");   
		HttpResponse response = client.execute(get);   
		String deptXML = String.valueOf(EntityUtils.toString(response.getEntity()));
		Map map = new SoapUtil().parse(deptXML);
		return setSuccessModelMap(modelMap,map);
	}
	
	/* 根据虚拟机id获取vnc参数 */  
	@RequestMapping(value = "/vnc", method = RequestMethod.GET)
	public Object getVnc(HttpServletRequest request, ModelMap modelMap, long vmid) throws Exception {
		DefaultHttpClient client = newInstance();   
		HttpGet get = new HttpGet(vncUrl + "/" + vmid);   
		get.addHeader("accept", "application/xml");   
		HttpResponse response = client.execute(get);   
		String deptXML = String.valueOf(EntityUtils.toString(response.getEntity()));
		Map map = new SoapUtil().parse(deptXML);
		return setSuccessModelMap(modelMap,map);
	}
	
}