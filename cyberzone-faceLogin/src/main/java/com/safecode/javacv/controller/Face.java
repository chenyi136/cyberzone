package com.safecode.javacv.controller;

import com.safecode.javacv.dto.ResponseData;
import com.safecode.javacv.face.FaceDetection;
import com.safecode.javacv.pojo.SysUser;
import com.safecode.javacv.service.SessionProvider;
import com.safecode.javacv.service.impl.FaceLoginServiceImpl;
import com.safecode.javacv.utils.Base64Utils;
import com.safecode.javacv.utils.TicketUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;


/**
 * 人脸识别controller
 *
 * @author safecode By 2018.12.8
 */
//@CrossOrigin
@Controller
@PropertySource(value = "classpath:face.properties")
public class Face {

    @Value("${imgfileoutpath}")
    private String filepath;

    @Resource
    SessionProvider sessionProvider;

    @Resource
    FaceLoginServiceImpl faceLoginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @Async
    public ListenableFuture<ResponseData> Facerecoregister(@RequestParam(value = "face") String imgFile, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws ExecutionException, InterruptedException {
        String token = TicketUtil.getTOKEN(httpServletRequest, httpServletResponse);
        SysUser attributeForSysUser = sessionProvider.getAttributeForSysUser(token);
        String register = faceLoginService.register(imgFile, filepath, attributeForSysUser.getAccount(), attributeForSysUser.getId().intValue()).get();
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        if ("faild".equals(register)) {
            responseData.setMsg("faild");
            return new AsyncResult<>(responseData);
        } else {
            responseData.setMsg("success");
            responseData.setData(register);
            return new AsyncResult<>(responseData);
        }
    }


    @RequestMapping(value = "/face", method = RequestMethod.POST)
    @ResponseBody
    @Async
    public ListenableFuture<ResponseData> facescan(@RequestParam(value = "face") String imgFile, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = TicketUtil.getTOKEN(httpServletRequest, httpServletResponse);
        SysUser attributeForSysUser = sessionProvider.getAttributeForSysUser(token);
        Base64Utils base64Utils = new Base64Utils();
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        try {
            String baseforfile = base64Utils.baseforfile(imgFile, attributeForSysUser.getFaceId());
            System.out.println("生成文件路径" + baseforfile);
            if (baseforfile != null) {
                boolean faceissuccess = faceLoginService.faceissuccess(baseforfile, attributeForSysUser.getFaceId()).get();
                if (faceissuccess) {
                    responseData.setMsg("人脸登录成功");
                    responseData.setData(true);
                    return new AsyncResult<>(responseData);
                } else {
                    responseData.setMsg("人脸登录失败,与库文件识别不一致");
                    responseData.setData(false);
                        return new AsyncResult<>(responseData);
                }
            } else {
                responseData.setMsg("人脸登录失败，人脸模型生成错误");
                responseData.setData(false);
                return new AsyncResult<>(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setMsg("人脸登录失败，程序内部错误");
            responseData.setData(false);
            return new AsyncResult<>(responseData);
        }
    }


    @RequestMapping("/test")
    @ResponseBody
    public String kk(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = TicketUtil.getTOKEN(httpServletRequest, httpServletResponse);
        SysUser attributeForSysUser = sessionProvider.getAttributeForSysUser(token);
        System.out.println(attributeForSysUser.getAccount());
        String filepath = "E:\\face\\hhh\\1545115991629.jpg";
        FaceDetection faceDetection=new FaceDetection();
        faceDetection.detectFace(filepath, filepath);
        return "fsdf";
    }

}