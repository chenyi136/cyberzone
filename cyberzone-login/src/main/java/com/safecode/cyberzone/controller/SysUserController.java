package com.safecode.cyberzone.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safecode.cyberzone.base.utils.TicketUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.safecode.cyberzone.dto.JsonResponse;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.service.SessionProvider;
import com.safecode.cyberzone.service.SysUserService;
import com.safecode.cyberzone.utils.SysLogUtil;

@Controller
//@CrossOrigin
public class
SysUserController {
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private SysLogController sysLogController;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse<SysUser> checkLogin(@RequestBody SysUser sysUser, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        JsonResponse<SysUser> json = new JsonResponse<SysUser>();
        String token = TicketUtil.getTOKEN(request, response);
        if (null != token) {
            sessionProvider.delAttributeForSysUser(token);
        }
        SysUser dbSysUser = sysUserService.findSysUserByAccount(sysUser.getAccount(), sysUser.getPassword());
        if (dbSysUser != null) {
            dbSysUser.setFaceIsLogin(0);
            sessionProvider.setAttributeForSysUser(TicketUtil.setTOKEN(request, response), dbSysUser);
            Map<String, String> map = SysLogUtil.save(request, sysUser.getAccount(), "登录系统");
            sysLogController.add(map);
            json.setStatus(true);
            json.setSuccessMsg("登录成功");
        } else {
            json.setStatus(false);
            json.setErrorMsg("用户名或密码不正确");
        }
        return json;

    }

    @RequestMapping(value = "/faceLogin", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse<SysUser> faceLogin(@Param("isfaceLogin") String isfaceLogin, HttpServletRequest request,
                                           HttpServletResponse response) {
        JsonResponse<SysUser> json = new JsonResponse<SysUser>();
        String token = TicketUtil.getTOKEN(request, response);
        System.out.println(isfaceLogin);
        if (null != token && "success".equals(isfaceLogin)) {
            SysUser redisSysUser = sessionProvider.getAttributeForSysUser(token);
            if (redisSysUser.getFacePerm() != 0) {
                redisSysUser.setFaceIsLogin(1);
                sessionProvider.setAttributeForSysUser(token, redisSysUser);
                json.setStatus(true);
                json.setSuccessMsg("人脸登录成功");
                return json;
            }
        }
        json.setStatus(false);
        json.setSuccessMsg("该用户未登录");
        return json;
    }

    @RequestMapping(value = "/isLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<SysUser> isLogin(HttpServletRequest request, HttpServletResponse response) {
        JsonResponse<SysUser> json = new JsonResponse<SysUser>();
        SysUser sysUser = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, response));
        if (sysUser != null) {
            json.setStatus(true);
            json.setSuccessMsg(sysUser.getAccount());
            return json;
        } else {
            json.setStatus(false);
            json.setErrorMsg("用户信息不存在");
            return json;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<SysUser> loginOut(HttpServletRequest request, HttpServletResponse response) {
        JsonResponse<SysUser> json = new JsonResponse<SysUser>();
        try {
            String token = TicketUtil.getTOKEN(request, response);
            if (token != null) {
                SysUser sysUser = sessionProvider.getAttributeForSysUser(token);
                sessionProvider.delAttributeForSysUser(token);
                Map<String, String> map = SysLogUtil.save(request, sysUser.getAccount(), "退出系统");
                sysLogController.add(map);
            }
            // 4,清除用户唯一登录票据
            TicketUtil.delTOKEN(request, response);
            json.setStatus(true);
            json.setSuccessMsg("用户信息已清除");
        } catch (Exception e) {
            json.setStatus(false);
        }
        return json;
    }

    @RequestMapping(value = "/sysTime", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<SysUser> sysTime() {
        JsonResponse<SysUser> json = new JsonResponse<SysUser>();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            json.setStatus(true);
            json.setSuccessMsg(String.valueOf(currentTimeMillis));
        } catch (Exception e) {
            json.setStatus(false);
            json.setErrorMsg("系统异常");
        }
        return json;
    }
}
