package com.safecode.cyberzone.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 常见的辅助类
 * 
 */
public class ContextUtils {
	 
	/**
	 * @func SpringMvc下获取request
	 * @author 皮锋
	 * @date 2016/12/26
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
 
	}
 
	/**
	 * @func SpringMvc下获取session
	 * @author 皮锋
	 * @date 2016/12/26
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
 
	}
 
}