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
import org.springframework.context.annotation.PropertySource;
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
import com.safecode.cyberzone.utils.UUIDUtils;
import com.safecode.cyberzone.vo.DetectionAuditVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin
@Controller
@PropertySource(value = "classpath:detection.properties",encoding = "utf-8")
@RequestMapping(value = "api/v1/DetectionAppls")
@Api(description = "测评申请接口")
public class DetectionApplController {

    private static Logger log = LoggerFactory.getLogger(DetectionApplController.class);
    @Autowired
    private DetectionApplService detectionApplService;

    @Autowired
    private DetectionAuditService detectionAuditService;

    @Autowired
    private SysLogController sysLogController;

    @Autowired
    private SessionProvider sessionProvider;

    @Value("${zip.file.path}")
    private String zipPath;

    @Value("${wasProd}")
    private boolean wasProd;

    @ApiOperation(value = "分页查询申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",paramType = "int",required = true),
            @ApiImplicitParam(name="rows",value = "每页显示条数",paramType = "int",required = true),
            @ApiImplicitParam(name="taskName",value = "任务名称",paramType = "String"),
            @ApiImplicitParam(name="userName",value = "姓名",paramType = "String"),
            @ApiImplicitParam(name="userTelephone",value = "联系电话",paramType = "String"),
            @ApiImplicitParam(name="userEmail",value = "邮箱",paramType = "String"),
            @ApiImplicitParam(name="companyName",value = "单位名称",paramType = "String"),
            @ApiImplicitParam(name="companyStation",value = "单位地址",paramType = "String"),
            @ApiImplicitParam(name="deviceName",value = "软件名称",paramType = "String"),
            @ApiImplicitParam(name="source",value = "页面来源【cyberzone为 1，其他为0】",paramType = "String"),
            @ApiImplicitParam(name = "type", value = "检测类型【软件类型(0); 硬件类型(1); 芯片类型(2); 漏洞类型(3);】", dataType = "String", required = false),
            @ApiImplicitParam(name="source",value = "页面来源【cyberzone为 1，其他为0】",paramType = "String",required = false),
            @ApiImplicitParam(name = "status", value = "状态状态【未提交（0); 检测中(2); 检测未通过(3);检测通过(4); 检测完成(5)；】", dataType = "String", required = false)
    })
    @RequestMapping(value = "findDetectionApplsByPage",method = RequestMethod.GET)
    @ResponseBody
    public  PageInfo<DetectionAppl>   findDetectionApplsByPage(HttpServletRequest request,
                                                               Integer page, Integer rows, DetectionAppl detectionAppl,String source){

        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        if(DetectionApplConsts.SOURCE_PAGE.equals(source)){ //从cyberzone来的 默认是测评人员

        }else{
            detectionAppl.setCreaterAccount(user.getId());
        }
        PageInfo<DetectionAppl> pageInfo = null;
        try {
            pageInfo = this.detectionApplService.findDetectionApplsByPage(detectionAppl,page,rows);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pageInfo;
    }

    @ApiOperation(value = "根据id查询单条申请数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",paramType = "String",required = true),
    })
    @RequestMapping(value = "findDetectionApplById",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DetectionAppl> findDetectionApplById(HttpServletRequest request, String id, String source){
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(StringUtils.isEmpty(id)){
            log.error("没有申请评测id", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有申请评测id！");
            return responseData;
        }
        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }

        responseData = this.detectionApplService.findDetectionApplById(id);

        if (responseData.getData() == null){
            log.error("根据id没有查询到申请评测信息", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("根据id没有查询到申请评测信息！");
            return responseData;
        }
        return responseData;
    }

    @RequestMapping(value = "total",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseData<Integer> findDetectionApplTotal(HttpServletRequest request, DetectionAppl detectionAppl){
        ResponseData<Integer> responseData = new ResponseData<>();
        try {
            responseData = this.detectionApplService.findDetectionApplTotal(detectionAppl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }


    @ApiOperation(value = "添加一条检测申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name="taskName",value = "任务名称",paramType = "String"),
            @ApiImplicitParam(name="taskDescription",value = "任务描述",paramType = "String"),
            @ApiImplicitParam(name="userName",value = "姓名",paramType = "String"),
            @ApiImplicitParam(name="userTelephone",value = "联系电话",paramType = "String"),
            @ApiImplicitParam(name="userEmail",value = "邮箱",paramType = "String"),
            @ApiImplicitParam(name="companyName",value = "单位名称",paramType = "String"),
            @ApiImplicitParam(name="companyStation",value = "单位地址",paramType = "String"),
            @ApiImplicitParam(name="userDescription",value = "备注",paramType = "String"),
            @ApiImplicitParam(name="deviceName",value = "软件名称",paramType = "String"),
            @ApiImplicitParam(name="deviceDescription",value = "软件描述",paramType = "String"),
            @ApiImplicitParam(name="upload",value = "上传的文件",paramType = "file"),
    })
    @RequestMapping(value = "addDetectionAppl",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DetectionAppl> addDetectionAppl (MultipartFile upload , DetectionAppl detectionAppl,HttpServletRequest request){
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(!StringUtils.isEmpty(detectionAppl.getId())){
            log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("违规插入数据！");
            return  responseData;
        }else{
            detectionAppl.setId(UUIDUtils.getUUID());
        }
        try {

            SysUser user=  wasFindUserMessage(request);
            if(user == null){
                log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
                return null;
            }
            log.error("wasFindUserMessage这是用户信息："+user);
            detectionAppl.setCreaterAccount(user.getId());
            detectionAppl.setCreater(user.getUserName());
            responseData = this.detectionApplService.addDetectionAppl(detectionAppl,upload);

            if(responseData.getData() !=null){
                try {
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"添加检测申请id:"+responseData.getData().getId());
                    sysLogController.add(map);
                }catch (Exception e){
                    log.error("IO异常,添加日志失败", request.getMethod(), request.getRequestURL().toString());
                }

            }

        } catch (IOException e) {
            log.error("IO异常,文件上传出现异常", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }

        return responseData;
    }

    @ApiOperation(value = "修改一条检测申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",paramType = "String",required = true),
            @ApiImplicitParam(name="taskName",value = "任务名称",paramType = "String"),
            @ApiImplicitParam(name="taskDescription",value = "任务描述",paramType = "String"),
            @ApiImplicitParam(name="userName",value = "姓名",paramType = "String"),
            @ApiImplicitParam(name="userTelephone",value = "联系电话",paramType = "String"),
            @ApiImplicitParam(name="userEmail",value = "邮箱",paramType = "String"),
            @ApiImplicitParam(name="companyName",value = "单位名称",paramType = "String"),
            @ApiImplicitParam(name="companyStation",value = "单位地址",paramType = "String"),
            @ApiImplicitParam(name="userDescription",value = "备注",paramType = "String"),
            @ApiImplicitParam(name="deviceName",value = "软件名称",paramType = "String"),
            @ApiImplicitParam(name="deviceDescription",value = "软件描述",paramType = "String"),
            @ApiImplicitParam(name="upload",value = "上传的文件",paramType = "file"),
    })
    @RequestMapping(value = "putDetectionAppl",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseData<DetectionAppl> putDetectionAppl(MultipartFile upload , DetectionAppl detectionAppl,HttpServletRequest request){
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(detectionAppl.getId() ==null){
            log.error("id不能为空", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空！");
            return  responseData;
        }
        try {
            responseData= this.detectionApplService.putDetectionAppl(detectionAppl,upload);
            SysUser user=  wasFindUserMessage(request);
            if(user == null){
                log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
                return null;
            }
            if(responseData.getData() !=null){
                try{
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"修改检测申请id："+responseData.getData().getId());
                    sysLogController.add(map);
                }catch (Exception e){
                    log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                }

            }

        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }


        return responseData;
    }

    @ApiOperation(value = "根据id删除单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",paramType = "String",required = true),
    })
    @RequestMapping(value = "delDetectionAppl",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DetectionAppl> delDetectionAppl(String id, HttpServletRequest request){
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(id ==null ||"".equals(id.toString())){
            log.error("id不能为空", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空！");
            return  responseData;
        }
        try {
            responseData = this.detectionApplService.delDetectionAppl(id);
            SysUser user=  wasFindUserMessage(request);
            if(user == null){
                log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
                return null;
            }
            if(responseData.getData() !=null){
                try {
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"删除检测申请id:"+responseData.getData().getId());
                    sysLogController.add(map);
                }catch (Exception e){
                    log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                }

            }
        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }

        return responseData;
    }

    @ApiOperation(value = "提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",paramType = "String",required = true),
    })
    @RequestMapping(value = "beginDetectionAppl",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DetectionAppl> beginDetectionAppl(HttpServletRequest request,DetectionAppl detectionAppl){
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(detectionAppl.getId() == null){
            log.error("id不能为空", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空！");
            return  responseData;
        }
        try {
            responseData = this.detectionApplService.beginDetectionAppl(detectionAppl);

            SysUser user=  wasFindUserMessage(request);
            if(user == null){
                log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
                return null;
            }
            if(responseData.getData() !=null){
                try {
                    String account = user.getAccount();
                    Map<String, String> map = null;
                    map = SysLogUtil.save(request,account,"提交检测申请id:"+responseData.getData().getId());
                    sysLogController.add(map);
                }catch (Exception e){
                    log.error("添加日志失败", request.getMethod(), request.getRequestURL().toString());
                }

            }

        }catch (Exception e){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }

        return responseData;
    }

    /*
     * @Description:  下载
     * @param ${tags}
     * @return ${return_type}
     * @throws
     * @author xuq
     * @date 2018/8/8 15:29
     */
    @ApiOperation(value = "下载检测报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "测评ID",paramType = "String",required = true),
    })
    @RequestMapping(value = "downLoad",method = RequestMethod.GET)
    public void downLoad( String id, HttpServletRequest request, HttpServletResponse res) {
        BufferedInputStream bis = null;
        OutputStream os = null;
        InputStream input = null;
        String fileName = "";
        try {
            String header = request.getHeader("User-Agent");
            ResponseData<DetectionAudit> responseData = this.detectionApplService.findPathByApplId(id);
            DetectionAudit detectionAudit = responseData.getData();
            if(responseData.getData() == null){
                log.error("根据id没有查询上传的检测报告，无法下载", request.getMethod(), request.getRequestURL().toString());
                return;
            }
            File file = new File(detectionAudit.getPath());
            input = new FileInputStream(file);
            fileName = detectionAudit.getAuditFileName();
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
                SysUser user=  wasFindUserMessage(request);
                if(user == null){
                    log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
                    return ;
                }
                if(responseData.getData() !=null){
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"下载检测申请id:"+responseData.getData().getId());
                    sysLogController.add(map);
                }
            }catch(Exception e){
                log.error("单个下载时添加日志失败", request.getMethod(), request.getRequestURL().toString());
            }

        } catch (Exception e) {
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
    @ApiOperation(value = "批量下载检测PDF文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "申请id的集合", paramType = "List", required = true)
    })
    @RequestMapping(value = "downLoadBatch", method = RequestMethod.GET)
    public void downLoadBatch(HttpServletRequest request, HttpServletResponse response, DetectionAuditVo detectionAuditVo) throws Exception {
        String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();

        String strZipPath = zipPath +"/"+ System.currentTimeMillis() + ".zip";
        File file = new File(zipPath);
        if( detectionAuditVo.getIds()== null || detectionAuditVo.getIds().size()<=0){
            return;
        }
        //根据ids查询数据集合

        List<DetectionAudit> detectionAudits = this.detectionAuditService.findDetectionAuditsByIds(detectionAuditVo.getIds());

        if (!file.isDirectory() && !file.exists()) {
            // 创建多层目录
            file.mkdirs();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
        for (int i = 0; i < detectionAudits.size(); i++) {
            DetectionAudit detectionAudit = detectionAudits.get(i);
            File f = new File(detectionAudit.getPath());
            FileInputStream fis = new FileInputStream(f);
            out.putNextEntry(new ZipEntry(detectionAudit.getAuditFileName()));
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
            SysUser user = sessionProvider.getAttributeForSysUser(TicketUtil.getTOKEN(request, null));

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
            Map<String, String> map = SysLogUtil.save(request,account,"批量下载检测申请ids:"+idsStr);
            sysLogController.add(map);
        }catch(Exception e){
            log.error("批量下载时添加日志失败", request.getMethod(), request.getRequestURL().toString());
        }
    }
    public  void downLoadFile(HttpServletRequest request,HttpServletResponse response,String filePath,String filename){
        try {
            File file=new File(filePath);
// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称 用于浏览器的下载框中自动显示的文件名
            String userAgent =request.getHeader("User-Agent");
            if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
                filename= URLEncoder.encode(filename,"UTF-8");
            }else{
                filename=new String(filename.getBytes("utf-8"),"iso8859-1");
            }

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


    @ApiOperation(value = "分页查询通知公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",paramType = "int",required = true),
            @ApiImplicitParam(name="rows",value = "每页显示条数",paramType = "int",required = true),
            @ApiImplicitParam(name="taskName",value = "任务名称",paramType = "String"),
            @ApiImplicitParam(name="task_description",value="任务名称",paramType="String"),
            @ApiImplicitParam(name="userName",value = "姓名",paramType = "String"),
            @ApiImplicitParam(name="userEmail",value = "邮箱",paramType = "String"),
            @ApiImplicitParam(name="companyName",value = "单位名称",paramType = "String"),
            @ApiImplicitParam(name="companyStation",value = "单位地址",paramType = "String"),
            @ApiImplicitParam(name="userTelephone",value = "联系电话",paramType = "String"),
            @ApiImplicitParam(name="user_description",value="描述",paramType="String"),
            @ApiImplicitParam(name="deviceName",value = "软件名称",paramType = "String"),
            @ApiImplicitParam(name="device_description",value="设备描述",paramType="String"),
            @ApiImplicitParam(name="status",value="状态",paramType="String"),
            @ApiImplicitParam(name="safety_check",value="执行检测",paramType="String"),
            @ApiImplicitParam(name="type",value="检测类型",paramType="String"),
            @ApiImplicitParam(name="url",value="网址",paramType="String"),
            @ApiImplicitParam(name="appl_path",value="申请人上传路径",paramType="String"),
            @ApiImplicitParam(name="appl_real_file_name",value="申请人上传的文件名",paramType="String"),
            @ApiImplicitParam(name="appl_file_size",value="申请人上传的文件大小",paramType="String"),
            @ApiImplicitParam(name="appl_file_suffix",value="申请人上传的文件后缀",paramType="String"),
            @ApiImplicitParam(name="appl_storge_file_name",value="存储服务器的文件名称",paramType="String"),
    })
    @RequestMapping(value = "findNoticeBulletinByPage",method = RequestMethod.GET)
    @ResponseBody
    public  PageInfo<DetectionAppl>   findNoticeBulletinByPage(HttpServletRequest request,
                                                               Integer page, Integer rows, DetectionAppl detectionAppl,String source){

        SysUser user=  wasFindUserMessage(request);
        if(user == null){
            log.error("没有获取到用户信息", request.getMethod(), request.getRequestURL().toString());
            return null;
        }
        if(DetectionApplConsts.SOURCE_PAGE.equals(source)){ //从cyberzone来的 默认是测评人员

        }else{
            detectionAppl.setCreaterAccount(user.getId());
        }
        PageInfo<DetectionAppl> pageInfo = null;
        detectionAppl.setCreaterAccount(user.getId());
        try {
            pageInfo = this.detectionApplService.findNoticeBulletinByPage(detectionAppl,page,rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }


    @ApiOperation(value = "根据id查询通知公告详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "ID",paramType = "String",required = true)
    })
    @RequestMapping(value = "findNoticeBulletinById",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DetectionAuditVo>  findNoticeBulletinById(HttpServletRequest request,String id,String source){

        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        if(StringUtils.isEmpty(id)){
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

        responseData = this.detectionApplService.findNoticeBulletinById(id);

        if(responseData == null){
            log.error("responseData不能为空", request.getMethod(), request.getRequestURL().toString());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("id不能为空！");
            return  responseData;
        }else {
            try {
                if(responseData.getData() !=null){
                    String account = user.getAccount();
                    Map<String, String> map = SysLogUtil.save(request,account,"根据id查询通知公告详情:"+responseData.getData().getId());
                    sysLogController.add(map);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseData;
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
