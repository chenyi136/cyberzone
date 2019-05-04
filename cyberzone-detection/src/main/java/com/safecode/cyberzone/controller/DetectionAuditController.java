package com.safecode.cyberzone.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safecode.cyberzone.base.dto.ResponseData;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.service.SessionProvider;
import com.safecode.cyberzone.base.utils.TicketUtil;
import com.safecode.cyberzone.global.consts.DetectionApplConsts;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.service.DetectionApplService;
import com.safecode.cyberzone.service.DetectionAuditService;
import com.safecode.cyberzone.utils.CodeUtils;
import com.safecode.cyberzone.utils.SysLogUtil;
import com.safecode.cyberzone.vo.DetectionAuditVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin
@Controller
@RequestMapping(value = "api/v1/detectionAudits")
@Api(description = "测评与服务评测接口")
public class DetectionAuditController {

    private static Logger log = LoggerFactory.getLogger(DetectionApplController.class);

    @Autowired
    private DetectionAuditService detectionAuditService;

    @Autowired
    private DetectionApplService detectionApplService;

    @Autowired
    private SysLogController sysLogController;

    @Autowired
    private SessionProvider sessionProvider;

    @Value("${zip.file.path}")
    private String zipPath;


    @Value("${wasProd}")
    private boolean wasProd;

    @ApiOperation(value = "分页查询评测列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", dataType = "int", required = true),
            @ApiImplicitParam(name = "rows", value = "每页显示条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "status", value = "状态状态【未提交（0); 检测中(2); 检测未通过(3);检测通过(4); 检测完成(5)；】", dataType = "String", required = false),
            @ApiImplicitParam(name = "noPassStatus", value = "判断是否需要没通过审核的数据", dataType = "int", required = false),
            @ApiImplicitParam(name = "type", value = "检测类型【软件类型(0); 硬件类型(1); 芯片类型(2); 漏洞类型(3);】", dataType = "String", required = false),
            @ApiImplicitParam(name="source",value = "页面来源【cyberzone为 1，其他为0】",paramType = "String",required = false),
    })
    @RequestMapping(value = "findDetectionAuditsByPage", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<DetectionAuditVo> findDetectionAuditsByPage(Integer page, Integer rows,
                                                                DetectionAuditVo detectionAuditVo,HttpServletRequest request,HttpServletResponse response,String source) throws Exception {

        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }

        if(DetectionApplConsts.SOURCE_PAGE.equals(source)){ //从cyberzone来的 默认是测评人员

        }else{
            detectionAuditVo.setCreaterAccount(user.getId());
        }
        PageInfo<DetectionAuditVo> pageInfo = null;
        try {

            pageInfo = this.detectionAuditService.findDetectionAuditsByPage(detectionAuditVo, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @ApiOperation(value = "根据评测id查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评测ID",dataType = "String",required = true),
    })
    @RequestMapping(value = "findDetectionAuditAndAppByAuditId",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DetectionAuditVo> findDetectionAuditAndAppByAuditId(String id, HttpServletRequest request, String source){
        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        if(StringUtils.isEmpty(id)){
            log.error("没有评测id", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有评测id！");
            return responseData;
        }
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        responseData = this.detectionAuditService.findDetectionAuditAndAppByAuditId(id);
        if (responseData.getData() == null){
            log.error("根据id没有查询到评测信息", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("根据id没有查询到评测信息！");
            return responseData;
        }
        return responseData;

    }

    @ApiOperation(value = "安全性检测接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applId",value = "申请ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "safetyCheck", value = "执行检测【软件病毒检测(1)、软件恶意攻击检测(2)、软件压力检测(3)】", paramType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评测ID", paramType = "String")
    })
    @RequestMapping(value = "safetyCheck", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DetectionAuditVo> safetyCheck(DetectionAuditVo detectionAuditVo, HttpServletRequest request) {

        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        if (detectionAuditVo.getApplId() == null) {
            log.error("没有申请id", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有申请id！");
            return responseData;
        }
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        try {
            responseData = this.detectionAuditService.safetyCheck(detectionAuditVo);
            if(responseData.getData() !=null){

                String description = "";
                if(DetectionApplConsts.NO_PASS.equals(detectionAuditVo.getStatus())){ //未通过
                    description ="不同意审核id：";
                }else if(DetectionApplConsts.YES_PASS.equals(detectionAuditVo.getStatus())){//审核通过
                    description ="同意审核id：";
                }
                if(responseData.getData()!=null){
                    try {
                        String account = user.getAccount();
                        Map<String, String> map = SysLogUtil.save(request,account,description+responseData.getData().getId());
                        sysLogController.add(map);
                    }catch (Exception e){
                        log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                    }

                }

            }

        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
        return responseData;
    }

    @ApiOperation(value = "是否同意申请接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applId", value = "申请ID", required = true,dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否通过[未通过(3),通过(4)]", paramType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "评测ID", paramType = "String"),
            @ApiImplicitParam(name = "description", value = "不通过原因", paramType = "String")
    })
    @RequestMapping(value = "commitDedectionAudit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DetectionAuditVo> commitDedectionAudit(DetectionAuditVo detectionAuditVo, HttpServletRequest request) {

        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        if (detectionAuditVo.getApplId() == null) {
            log.error("没有申请id", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有申请id！");
            return responseData;
        }
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        try {
            responseData = this.detectionAuditService.commitDedectionAudit(detectionAuditVo,user);
            if(responseData.getData() !=null){

                String description = "";
                if(DetectionApplConsts.NO_PASS.equals(detectionAuditVo.getStatus())){ //未通过
                    description ="不同意审核id：";
                }else if(DetectionApplConsts.YES_PASS.equals(detectionAuditVo.getStatus())){//审核通过
                    description ="同意审核id：";
                }
                if(responseData.getData()!=null){
                    try {
                        String account = user.getAccount();
                        Map<String, String> map = SysLogUtil.save(request,account,description+responseData.getData().getId());
                        sysLogController.add(map);
                    }catch (Exception e){
                        log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
        return responseData;
    }

    @ApiOperation(value = "上传文件确认接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测评ID",dataType = "String", required = true),
            @ApiImplicitParam(name = "upload", value = "上传的文件", paramType = "file", required = true)
    })
    @RequestMapping(value = "confirmDetectionAudit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DetectionAudit> confirmDetectionAudit(MultipartFile upload, DetectionAudit detectionAudit,
                                                                    HttpServletRequest request) {
        ResponseData<DetectionAudit> responseData = new ResponseData<>();
        if (upload == null) {
            log.error("没有上传文件", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有上传文件！");
            return responseData;
        }

        if (StringUtils.isEmpty(detectionAudit.getId())) {
            log.error("没有测评的id", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有测评的id！");
            return responseData;
        }

        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        try {
            responseData = this.detectionAuditService.confirmDetectionAudit(upload, detectionAudit);
            if(responseData.getData() !=null){
                if(responseData.getData()!=null){
                    try {
                        String account = user.getAccount();
                        Map<String, String> map = SysLogUtil.save(request,account,"确认提交报告id："+responseData.getData().getId());
                        sysLogController.add(map);
                    }catch (Exception e){
                        log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                    }
                }
            }

        }catch (IOException e) {
            log.error("IO异常,文件上传出现异常", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("上传失败");
            log.error("上传失败", request.getMethod(), request.getRequestURL().toString());
            e.printStackTrace();
        }
        return responseData;
    }

    @ApiOperation(value = "下载检测申请的文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测评ID",dataType = "String", required = true)
    })
    @RequestMapping(value = "downLoad", method = RequestMethod.GET)
    public void downLoad(String id, HttpServletRequest request, HttpServletResponse res) {
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return ;
        }
        BufferedInputStream bis = null;
        OutputStream os = null;
        InputStream input = null;
        String fileName = "";
        try {
            String header = request.getHeader("User-Agent");
            ResponseData<DetectionAppl> responseData = this.detectionApplService.findDetectionApplById(id);
            DetectionAppl detectionAppl = responseData.getData();
            if (responseData.getData() == null) {
                log.error("根据id没有查询到数据，无法下载", request.getMethod(), request.getRequestURL().toString());
                return;
            }
            File file = new File(detectionAppl.getApplPath());
            input = new FileInputStream(file);
            fileName = detectionAppl.getApplRealFileName();
            res.setHeader("content-type", "application/octet-stream");
            res.setContentType("application/octet-stream");
            if (header.contains("Firefox")) {
                fileName = CodeUtils.base64EncodeFileName(fileName);
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            byte[] buff = new byte[1024];

            os = res.getOutputStream();
            bis = new BufferedInputStream(input);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            try{
                if(responseData.getData() !=null){
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"下载报告id："+responseData.getData().getId());
                    sysLogController.add(map);
                }
            }catch(Exception e){
                log.error("下载时，添加日志错误", request.getMethod(), request.getRequestURL().toString());

            }

        } catch (IOException e) {
            log.error("根据路径读取文件失败，无法返回二进制流", request.getMethod(), request.getRequestURL().toString());
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @ApiOperation(value = "批量下载检测申请的文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "申请id的集合", dataType = "list",required = true)
    })
    @RequestMapping(value = "downLoadBatch", method = RequestMethod.GET)
    public void downLoadBatch(HttpServletRequest request, HttpServletResponse response,DetectionAuditVo detectionAuditVo) throws Exception {
        String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String header = request.getHeader("User-Agent");
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            return ;
        }
        if (header.contains("Firefox")) {
            filename = CodeUtils.base64EncodeFileName(filename);
        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        String strZipPath = zipPath +"/"+ filename + ".zip";
        File file = new File(zipPath);
        if( detectionAuditVo.getIds()== null || detectionAuditVo.getIds().size()<=0){
            return;
        }
        //根据ids查询数据集合
        List<DetectionAppl> detectionAppls = this.detectionApplService.findDetectionApplsByIds(detectionAuditVo.getIds());

        if (!file.isDirectory() && !file.exists()) {
            // 创建多层目录
            file.mkdirs();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
        for (int i = 0; i < detectionAppls.size(); i++) {
            DetectionAppl detectionAppl = detectionAppls.get(i);
            File f = new File(detectionAppl.getApplPath());
            FileInputStream fis = new FileInputStream(f);
            out.putNextEntry(new ZipEntry(detectionAppl.getApplRealFileName()));
            //设置压缩文件内的字符编码，不然会变成乱码
            out.setEncoding("UTF-8");
            int len;
            // 读入需要下载的文件的内容，打包到zip文件
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
        this.downLoadFile(request, response, strZipPath, filename + ".zip");
        File temp = new File(strZipPath);
        if (temp.exists()) {
            temp.delete();
        }
        try{
            String account = user.getAccount();
            String idsStr = "";
            if(detectionAuditVo.getIds() !=null && detectionAuditVo.getIds().size()>0){
                for(int i =0;i<detectionAuditVo.getIds().size();i++){
                    String s = detectionAuditVo.getIds().get(i);
                    if(i == detectionAuditVo.getIds().size()-1){
                        idsStr += s;
                    }else{
                        idsStr += s +";";
                    }
                }
            }
            Map<String, String> map = SysLogUtil.save(request,account,"批量下载报告 ids："+idsStr);
            sysLogController.add(map);

        }catch(Exception e){
            log.error("批量下载时，添加日志错误", request.getMethod(), request.getRequestURL().toString());
        }
    }
    public  void downLoadFile(HttpServletRequest request,HttpServletResponse response,String filePath,String filename){
        try {
            File file=new File(filePath);
// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称 用于浏览器的下载框中自动显示的文件名
            String userAgent =request.getHeader("User-Agent");

            byte[] b = new byte[1024];
            int len=0;
            FileInputStream fs=new FileInputStream(file);
            response.reset();
            OutputStream os = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" +filename );
            //response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/octet-stream");
            byte[] buff = new byte[1024];
            int i = fs.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = fs.read(buff);
            }

            fs.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("根据路径读取文件失败，无法返回二进制流", request.getMethod(), request.getRequestURL().toString());
            e.printStackTrace();
        }
    }

    /**
     *  获取用户信息
     * @param request
     * @param user
     * @return
     */
    public SysUser  wasFindUserMessage(HttpServletRequest request){

        SysUser  user = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null));
        if(wasProd){  //生产环境下
            if(user == null){
                return null;
            }
            return user;
        }else {  //开发环境下
            SysUser sysUser = new SysUser();
            return  sysUser;
        }

    }
}