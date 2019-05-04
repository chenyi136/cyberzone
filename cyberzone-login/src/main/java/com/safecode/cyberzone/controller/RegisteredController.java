package com.safecode.cyberzone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safecode.cyberzone.base.utils.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safecode.cyberzone.dto.JsonResponse;
import com.safecode.cyberzone.pojo.SysUser;
import com.safecode.cyberzone.service.SessionProvider;
import com.safecode.cyberzone.service.SysUserService;

//@CrossOrigin
@RestController
@RequestMapping("/user")
public class RegisteredController {


    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/faceregister")
    public JsonResponse faceregister(HttpServletResponse response, HttpServletRequest request, String faceid) {
        JsonResponse jsonResponse = new JsonResponse();
        String token = TicketUtil.getTOKEN(request, response);
        SysUser redisSysUser = sessionProvider.getAttributeForSysUser(token);
        sysUserService.updateFaceIdForAccount(redisSysUser.getId(), faceid);
        redisSysUser.setFaceId(faceid);
        sessionProvider.setAttributeForSysUser(token, redisSysUser);
        jsonResponse.setStatus(true);
        jsonResponse.setSuccessMsg("人脸id注册成功");
        return jsonResponse;
    }

        @RequestMapping("/getfaceid")
    public JsonResponse getfaceID(HttpServletResponse response, HttpServletRequest request) {
        JsonResponse jsonResponse = new JsonResponse();
        SysUser redisSysUser = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, response));
        String faceId = sysUserService.selectFaceIdForUserId(redisSysUser.getId());
        jsonResponse.setStatus(true);
        jsonResponse.setSuccessMsg(faceId);
        return jsonResponse;
    }
}

