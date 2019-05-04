package com.safecode.cyberzone.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.cookie.CookieOrigin;

/**  
* <p>Title: TicketUtil</p>  
* <p>Description: 维护唯一票据（key）的工具类</p>  
* @author ludongbin  
* @date 2018年6月11日  
*/  
public class TicketUtil {
	
	/**
	 * 
	 * <p>Title: getJSESSIONID</p>  
	 * <p>Description: 维护唯一票据（key）的工具类</p>  
	 * @param request
	 * @param response
	 * @return
	 */
	
	public static String getJSESSIONID(HttpServletRequest request , HttpServletResponse response){
		// 1、判断cookie中是否存在该key
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if("JSESSIONID".equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		// 2、如果不存在，说明是第一次访问：需要将该key保存到cookie中
		String JSESSIONID = UUID.randomUUID().toString().replace("-", "");
		Cookie cookie = new Cookie("JSESSIONID",JSESSIONID);
		cookie.setMaxAge(60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
		return JSESSIONID;
	}
	/**
	 * 
	 * <p>Title: delJSESSIONID</p>  
	 * <p>Description: 清除cookie</p>  
	 * @param request
	 * @param response
	 */
	public static void delJSESSIONID(HttpServletRequest request , HttpServletResponse response){
		Cookie cookie = new Cookie("JSESSIONID" , null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
