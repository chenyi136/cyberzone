package com.safecode.cyberzone.base.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class TicketUtil {


    public static String setTOKEN(HttpServletRequest request, HttpServletResponse response) {
        // 1、判断cookie中是否存在该key
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("TOKEN".equals(cookie.getName())) {
                    cookie.setMaxAge(-1);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return cookie.getValue();
                }
            }
        }
        // 2、如果不存在，说明是第一次访问：需要将该key保存到cookie中
        String TOKEN = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("TOKEN", TOKEN);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        return TOKEN;
    }

    public static String getTOKEN(HttpServletRequest request, HttpServletResponse response) {
        // 1、判断cookie中是否存在该key
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void delTOKEN(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }



}
