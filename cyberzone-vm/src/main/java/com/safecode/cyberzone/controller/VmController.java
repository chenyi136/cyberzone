package com.safecode.cyberzone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.safecode.cyberzone.controller.base.BaseController;
import com.safecode.cyberzone.utils.HttpsUtils;

@CrossOrigin
@RestController
@RequestMapping(value = "vm")
public class VmController extends BaseController {
	
	Map<String, String> map = new HashMap<String, String>();
	
	@Value("${sxf.ticket.url}") private String ticketUrl;//深信服登录验证url
	@Value("${sxf.vms.url}") private String vmsUrl;//深信服获取vm列表url
	@Value("${sxf.ips.url}") private String ipsUrl;//深信服获取vm列表url
	@Value("${sxf.vncproxy.url.begin}") private String vncproxyBUrl;//深信服获取某台vm机具体信息  前半段
	@Value("${sxf.vncproxy.url.end}") private String vncproxyEUrl;//深信服获取某台vm机具体信息  后半段
	@Value("${sxf.clone.url.end}") private String cloneEUrl;//深信服虚拟机克隆 后半段
	@Value("${sxf.username}") private String username;//用户名
	@Value("${sxf.password}") private String password;//密码
	@Value("${sxf.vncws}") private String vncws;//默认参数
	@Value("${sxf.groupname}") private String groupname;//虚拟机分组名称
	
	private String Coo = "";
	private String Coo1 = "";
	private String tik = "";
	
	/**
     * 深信服-用户身份验证
     * @param request
     * @param modelMap
     * @return
	 * @throws Exception 
     */
	@RequestMapping(value = "/ticket", method = RequestMethod.POST)
	public Object getTicket(HttpServletRequest request, ModelMap modelMap) throws Exception {
		
		HttpSession session = request.getSession(); 
		Map<String, Object> params = BaseController.getParameterMap(request);
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		params.put("username", username);
		params.put("password", password);
		try {
			String jsonStr = HttpsUtils.doPost(ticketUrl, params, sessionMap, null);
			if(jsonStr != null && !jsonStr.equals("")) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String ticket = (String) ((JSONObject) json.get("data")).get("ticket");
				String CSRFPreventionToken = (String) ((JSONObject) json.get("data")).get("CSRFPreventionToken");
				session.setAttribute("Cookie", ticket);
				session.setAttribute("CSRFPreventionToken", CSRFPreventionToken);
				Coo1 = ticket;
				Coo = "LoginAuthCookie=" + ticket;
				tik = CSRFPreventionToken;
				
				resultMap = JSON.parseObject(jsonStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
		return setSuccessModelMap(modelMap,resultMap);
	}
	
	
	public void getTicket1() throws Exception {
		
		System.out.println("conme in this .......--------------------------------------------------------------------------");
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		params.put("username", username);
		params.put("password", password);
		try {
			String jsonStr = HttpsUtils.doPost(ticketUrl, params, sessionMap, null);
			if(jsonStr != null && !jsonStr.equals("")) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String ticket = (String) ((JSONObject) json.get("data")).get("ticket");
				String CSRFPreventionToken = (String) ((JSONObject) json.get("data")).get("CSRFPreventionToken");
//				session.setAttribute("Cookie", ticket);
//				session.setAttribute("CSRFPreventionToken", CSRFPreventionToken);
				Coo1 = ticket;
				Coo = "LoginAuthCookie=" + ticket;
				tik = CSRFPreventionToken;
				
				resultMap = JSON.parseObject(jsonStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
	}
	
	/**
	 * 将ip按对应关系组装到虚拟机列表信息中
	 * @param vmsResultMap 虚拟机信息列表
	 * @param ipsResultMap ip数据列表
	 * @return 返回组装后数据列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> assembleIp(Map<String, Object> vmsResultMap, Map<String, Object> ipsResultMap) {
		List vmsList = (List) vmsResultMap.get("data");
		Map<String, Object> ipsMap = (Map<String, Object>) ipsResultMap.get("data");
		try {
			for(int i=0; i< vmsList.size(); i++) {
				Map<String, Object> vmMap = (Map<String, Object>) vmsList.get(i);
				String host = (String) vmMap.get("host");
				Long vmId = (Long) vmMap.get("vmid");
				if(!host.equals("") && host != null && !vmId.equals("") && vmId != null) {
					List iplist = new ArrayList();
					if((JSON.parseObject(String.valueOf(ipsMap.get(host)))).containsKey(String.valueOf(vmId))) {
						if(!((JSONObject) (JSON.parseObject(String.valueOf(ipsMap.get(host)))).get(String.valueOf(vmId))).isEmpty()) {
							JSONObject ipmoreMap = (JSONObject) (JSON.parseObject(String.valueOf(ipsMap.get(host)))).get(String.valueOf(vmId));
							for(int j=0; j<=20; j++) {
								String mk = "net" + j;
								String key = "";
								Map ipMap = (JSONObject) ipmoreMap.get(mk);
								if(ipMap == null || ipMap.isEmpty()) {
									vmMap.put("vmip", iplist);
									break;
								}else {
									String ip = (String) ((ipMap.keySet()).toArray())[0];
									iplist.add(ip);
								}
							}
						}
					}else {
						vmMap.put("vmip", iplist);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
		return vmsResultMap;
	}
	
	/**
	 * 获取指定虚拟机分组中的虚拟机列表
	 * @param vmsResultMap 虚拟机信息列表
	 * @return 返回组装后数据列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> vmsByGroupName(Map<String, Object> vmsResultMap) {
		
		List vmsList = (List) vmsResultMap.get("data");
		List vmsListNew = new ArrayList();
		for(int i=0; i<vmsList.size(); i++) {
			if(String.valueOf(((JSONObject) vmsList.get(i)).get("groupname")).equals(groupname)) {
				vmsListNew.add(vmsList.get(i));
			}
		}
		vmsResultMap.put("data", vmsListNew);
		return vmsResultMap;
	}

	/**
     * 深信服-获取vm列表
     * @param request
     * @param modelMap
     * @return
     */
	@RequestMapping(value = "/vms", method = RequestMethod.GET)
	public Object getVm(HttpServletRequest request, ModelMap modelMap) throws IOException, Exception {
		
		getTicket1();
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Map<String, Object> vmsResultMap = new HashMap<String, Object>();
		Map<String, Object> ipsResultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(); 
//		String Cookie = (String) session.getAttribute("Cookie");
//		String CSRFPreventionToken = (String) session.getAttribute("CSRFPreventionToken");
		String Cookie = Coo1;
		String CSRFPreventionToken = tik;
		
		System.out.println("Cookie = " + Cookie + " ;      CSRFPreventionToken = " + CSRFPreventionToken);
		try {
			if(Cookie != null && CSRFPreventionToken != null) {
				sessionMap.put("Cookie", Cookie);
				sessionMap.put("CSRFPreventionToken", CSRFPreventionToken);
	    		String vmsJsonStr = HttpsUtils.doGet(vmsUrl, param, sessionMap, null);
	    		String ipsJsonStr = HttpsUtils.doGet(ipsUrl, param, sessionMap, null);
	        	if(vmsJsonStr != null && !vmsJsonStr.equals("")) {
	        		vmsResultMap = JSON.parseObject(vmsJsonStr);
	        		vmsResultMap = vmsByGroupName(vmsResultMap);
	    		}
	        	
	        	if(ipsJsonStr != null && !ipsJsonStr.equals("")) {
	        		ipsResultMap = JSON.parseObject(ipsJsonStr);
	    		}
	        	
	        	if(!ipsJsonStr.isEmpty()) {
	        		ipsResultMap = assembleIp(vmsResultMap, ipsResultMap);
	        	} else {
	        		vmsResultMap.put("flag", "false");
		    		vmsResultMap.put("message", "未获取虚拟机IP！");
	        	}
	        	
	        	
	    	}else {
	    		vmsResultMap.put("flag", "false");
	    		vmsResultMap.put("message", "身份验证不通过！");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
		
    	return setSuccessModelMap(modelMap,vmsResultMap);
    }
	
	/**
     * 深信服-获取单独一台vnc信息
     * @param request
     * @param modelMap
     * @return
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/vncProxy", method = RequestMethod.POST)
	public Object getVncProxy(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException, Exception {
		
		getTicket1();
		
		Map<String, Object> params = BaseController.getParameterMap(request);
		System.out.println("params = " + params);
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(); 
//		String Cookie = (String) session.getAttribute("Cookie");
//		String CSRFPreventionToken = (String) session.getAttribute("CSRFPreventionToken");
		String Cookie = Coo1;
		String CSRFPreventionToken = tik;
		if(Cookie != null && CSRFPreventionToken != null) {
			System.out.println("Cookie = " +Cookie + ";             CSRFPreventionToken=" + CSRFPreventionToken);
//			String vmid = (String) params.get("mem_used");
			String vmid = (String) params.get("vmId");
			if(vmid == null && vmid.equals("")) {
				vmid = "2167692539934";
			}
			System.out.println("vmid = " + vmid);
			if(vmid != null) {
				String url = vncproxyBUrl + vmid + vncproxyEUrl;
				params = new HashMap<String, Object>();
				params.put("vncws", vncws);
				sessionMap.put("Cookie", Cookie);
				sessionMap.put("CSRFPreventionToken", CSRFPreventionToken);
				
				System.out.println("url =" + url);
				System.out.println("params =" + params);
				System.out.println("sessionMap =" + sessionMap);
				String jsonStr = HttpsUtils.doPost(url, params, sessionMap, null);
				
				if(jsonStr != null && !jsonStr.equals("")) {
					resultMap = JSON.parseObject(jsonStr);
					
					((Map) resultMap.get("data")).put("cookie",Coo);
					((Map) resultMap.get("data")).put("token",tik);
					resultMap.put("cookie", Coo);
					resultMap.put("token", tik);
				}
			}
		} else {
			resultMap.put("flag", "false");
			resultMap.put("message", "身份验证不通过！");
		}
		
		return setSuccessModelMap(modelMap,resultMap);
	}
	
	/**
     * 深信服-虚拟机克隆
     * @param request
     * @param modelMap
     * @return
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cloneVM", method = RequestMethod.POST)
	public Object cloneVM(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException, Exception {
		
		getTicket1();
		
		Map<String, Object> params = BaseController.getParameterMap(request);
		System.out.println("params = " + params);
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(); 
		
		String Cookie = Coo1;
		String CSRFPreventionToken = tik;
		
		if(Cookie != null && CSRFPreventionToken != null) {
			
			sessionMap.put("Cookie", Cookie);
			sessionMap.put("CSRFPreventionToken", CSRFPreventionToken);
			
			String clonename = (String) params.get("clonename");
			String groupname = (String) params.get("groupname");
			String systemtype = (String) params.get("systemtype");
			
			Map vmMap = new HashMap();
			vmMap = getVmObject(sessionMap,systemtype);
			String vmid = String.valueOf(vmMap.get("vmid"));
			String storage = String.valueOf(vmMap.get("cfgstorage"));
			String targetnode = "cluster";
			String groupid = String.valueOf(vmMap.get("vmgroup"));
			if(vmid != null) {
				String url = vncproxyBUrl + vmid + cloneEUrl;
				params = new HashMap<String, Object>();
//				params.put("vmid", vmid);
				params.put("clonename", clonename);
				params.put("storage", storage);
				params.put("targetnode", targetnode);
				params.put("group", groupid);
				params.put("start", "1");
				String jsonStr = HttpsUtils.doPost(url, params, sessionMap, null);
				
				if(jsonStr != null && !jsonStr.equals("")) {
					resultMap = JSON.parseObject(jsonStr);
				}
				
			}
		} else {
			resultMap.put("flag", "false");
			resultMap.put("message", "身份验证不通过！");
		}
		
		return setSuccessModelMap(modelMap,resultMap);
	}

	/**
	 * 获取虚拟机列表
	 * @param sessionMap2
	 * @param systemtype
	 * @return
	 * @throws Exception
	 */
	private Map getVmObject(Map<String, Object> sessionMap2, String systemtype) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> vmsResultMap = new HashMap<String, Object>();
		Map<String, Object> ipsResultMap = new HashMap<String, Object>();
		Map map = new HashMap();
		try {
    		String vmsJsonStr = HttpsUtils.doGet(vmsUrl, param, sessionMap2, null);
        	if(vmsJsonStr != null && !vmsJsonStr.equals("")) {
        		vmsResultMap = JSON.parseObject(vmsJsonStr);
        		map = vmsByGroupNameAndSystemType(vmsResultMap,systemtype);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
    	return map;
	}
	
	/**
	 * 根据系统类型选择其中一条数据返回
	 * @param vmsResultMap
	 * @param systemtype
	 * @return
	 */
	public Map<String, Object> vmsByGroupNameAndSystemType(Map<String, Object> vmsResultMap, String systemtype) {
		Map map = new HashMap();
		List vmsList = (List) vmsResultMap.get("data");
		List vmsListNew = new ArrayList();
		for(int i=0; i<vmsList.size(); i++) {
			if(String.valueOf(((JSONObject) vmsList.get(i)).get("groupname")).equals(groupname)) {
				if((String.valueOf(((JSONObject) vmsList.get(i)).get("ostype"))).contains(systemtype)) {
					map = (Map) vmsList.get(i);
					return map;
				} else if((String.valueOf(((JSONObject) vmsList.get(i)).get("ostype"))).equals("l2664") 
						&& (String.valueOf(((JSONObject) vmsList.get(i)).get("osname"))) != null 
						&& !(String.valueOf(((JSONObject) vmsList.get(i)).get("osname"))).equals("") 
						&& (String.valueOf(((JSONObject) vmsList.get(i)).get("osname"))).equals(systemtype)) {
					map = (Map) vmsList.get(i);
					return map;
				} else if ((String.valueOf(((JSONObject) vmsList.get(i)).get("ostype"))).equals("l2664") 
						&& (String.valueOf(((JSONObject) vmsList.get(i)).get("osname"))).equals("null") 
						&& systemtype.equals("linux-kali")) {
					map = (Map) vmsList.get(i);
					return map;
	    		}
			}
		}
		return map;
	}
}
