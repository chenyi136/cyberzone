package com.safecode.cyberzone.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.safecode.cyberzone.base.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.global.consts.DetectionApplConsts;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.mapper.DetectionApplMapper;
import com.safecode.cyberzone.mapper.DetectionAuditMapper;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.service.DetectionApplService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.vo.DetectionAuditVo;

@Service
@Transactional
public class DetectionApplServiceImpl implements DetectionApplService {


    @Autowired
    private DetectionApplMapper detectionApplMapper;

    @Autowired
    private DetectionAuditMapper detectionAuditMapper;

    @Value("${appl.file.path}")
    private String filePath;

    @Value("${appl.file.size}")
    private Integer fileSize;

    @Override
    public ResponseData<DetectionAppl> addDetectionAppl(DetectionAppl detectionAppl, MultipartFile upload) throws IOException {
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        if(upload !=null){
            String filename = "/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            String path = filePath +filename;
            FileInfo fileInfo = FileUtil.saveFile(upload, path);
            //设置文件路径
            detectionAppl.setApplPath(fileInfo.getFilePath());

            //设置文件名称
            detectionAppl.setApplRealFileName(fileInfo.getFileName());
            //设置文件大小
            detectionAppl.setApplFileSize(fileInfo.getFileSize());
            //设置文件后缀
            detectionAppl.setApplFileSuffix(fileInfo.getFileSuffix());
            //设置存储的文件名称
            detectionAppl.setApplStorgeFileName(fileInfo.getRealName());
            detectionAppl.setCreateTime(new Date());
            //设置创建状态
            detectionAppl.setStatus(DetectionApplConsts.NO_COMMIT);
            this.detectionApplMapper.addDetectionAppl(detectionAppl);
            responseData.setData(detectionAppl);
        }else{
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("未检测到有附件上传！");
            return  responseData;
        }
        //未提交
        // detectionAppl.setStatus(DetectionApplConsts.NO_COMMIT);
        //设置创建时间

        return responseData;
    }

    @Override
    public ResponseData<DetectionAppl> delDetectionAppl(String id) throws Exception {

        //先根据id查询
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        DetectionAppl detectionAppl = this.detectionApplMapper.findDetectionApplById(id);
        if(detectionAppl ==null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有根据id查询到数据！");
            return  responseData;
        }
        this.detectionApplMapper.delDetectionApplById(id);
        responseData.setData(detectionAppl);
        return responseData;
    }

    @Override
    public PageInfo<DetectionAppl> findDetectionApplsByPage(DetectionAppl detectionAppl, Integer page, Integer rows) {
        if(page ==null){
            page =1;
        }
        if(rows ==null){
            rows =10;
        }
        detectionAppl.setPageNum(page);
        detectionAppl.setPageSize(rows);
        PageHelper.startPage(detectionAppl);
        Page<DetectionAppl> pg = this.detectionApplMapper.findDetectionApplsByPage(detectionAppl);
        return  new PageInfo<DetectionAppl>(pg);
    }

    @Override
    public ResponseData<List<DetectionAppl>> findDetectionAppls(DetectionAppl detectionAppl) {
        return null;
    }

    @Override
    public ResponseData<Integer> findDetectionApplTotal(DetectionAppl detectionAppl) throws Exception {
        ResponseData<Integer> responseData = new ResponseData<>();
        Integer total = this.detectionApplMapper.findDetectionApplTotal(detectionAppl);
        responseData.setData(total);
        return null;
    }

    @Override
    public ResponseData<DetectionAppl> findDetectionApplById(String id) {
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        DetectionAppl detectionAppl = this.detectionApplMapper.findDetectionApplById(id);
        responseData.setData(detectionAppl);
        return responseData;
    }

    @Override
    public ResponseData<DetectionAppl> putDetectionAppl(DetectionAppl detectionAppl, MultipartFile upload) throws IOException, Exception {

        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        //先根据id进行查询
        DetectionAppl detectionApplOld = this.detectionApplMapper.findDetectionApplById(detectionAppl.getId());

        if(detectionApplOld ==null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有根据id查询到数据！");
            return  responseData;
        }
        if(!StringUtils.isEmpty(upload) && org.apache.commons.lang.StringUtils.isNotEmpty(upload.getOriginalFilename())){
            FileInfo fileInfo = null;
            String filename = "/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            if(upload.getSize()>fileSize*1024*1024){
                //log.error("没有测评的id", request.getMethod(), request.getRequestURL().toString());
                responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
                responseData.setMsg("超出上传文件的限制范围"+fileSize+"Mb");
                return responseData;
            }
            String path = filePath + filename;
            fileInfo = FileUtil.saveFile(upload, path);
            //设置文件路径
            detectionApplOld.setApplPath(fileInfo.getFilePath());
            //设置文件名称
            detectionApplOld.setApplRealFileName(fileInfo.getFileName());
            //设置文件大小
            detectionApplOld.setApplFileSize(fileInfo.getFileSize());
            //设置文件后缀
            detectionApplOld.setApplFileSuffix(fileInfo.getFileSuffix());
            //设置存储的文件名称
            detectionApplOld.setApplStorgeFileName(fileInfo.getRealName());
        }
        //set任务名称
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getTaskName())){
            detectionApplOld.setTaskName(detectionAppl.getTaskName());
        }
        //set 任务描述
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getTaskDescription())){
            detectionApplOld.setTaskDescription(detectionAppl.getTaskDescription());
        }
        //set 用户名称
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getUserName())){
            detectionApplOld.setUserName(detectionAppl.getUserName());
        }
        //set 用户邮箱
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getUserEmail())){
            detectionApplOld.setUserEmail(detectionAppl.getUserEmail());
        }
        //set 公司名称
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getCompanyName())){
            detectionApplOld.setCompanyName(detectionAppl.getCompanyName());
        }
        //set 公司地址
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getCompanyStation())){
            detectionApplOld.setCompanyStation(detectionAppl.getCompanyStation());
        }
        // set 电话
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getUserTelephone())){
            detectionApplOld.setUserTelephone(detectionAppl.getUserTelephone());
        }
        // set 备注
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getUserDescription())){
            detectionApplOld.setUserDescription(detectionAppl.getUserDescription());
        }

        //set 设备名称
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getDeviceName())){
            detectionApplOld.setDeviceName(detectionAppl.getDeviceName());
        }

        //set 设备描述
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getDeviceDescription())){
            detectionApplOld.setDeviceDescription(detectionAppl.getDeviceDescription());
        }
        //set 状态
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getStatus())){
            detectionApplOld.setStatus(detectionAppl.getStatus());
        }
        //set 类型
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getType())){
            detectionApplOld.setType(detectionAppl.getType());
        }
        //set url
        if(org.apache.commons.lang.StringUtils.isNotBlank(detectionAppl.getUrl())){
            detectionApplOld.setUrl(detectionAppl.getUrl());
        }
        //set 更新时间
        detectionApplOld.setUpdateTime(new Date());
        this.detectionApplMapper.putDetectionAppl(detectionApplOld);
        responseData.setData(detectionApplOld);
        return responseData;
    }

    @Override
    public ResponseData<DetectionAppl> beginDetectionAppl(DetectionAppl detectionAppl) {
        ResponseData<DetectionAppl> responseData = new ResponseData<>();
        //先查询
        DetectionAppl detectionApplOld = this.detectionApplMapper.findDetectionApplById(detectionAppl.getId());
        if(detectionApplOld ==null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有根据id查询到数据！");
            return  responseData;
        }
        //改变申请状态
        detectionApplOld.setStatus(DetectionApplConsts.DETECTIONING);
        detectionApplOld.setUpdateTime(new Date());
        this.detectionApplMapper.putDetectionAppl(detectionApplOld);
        responseData.setData(detectionApplOld);

        return responseData;
    }

    @Override
    public ResponseData<DetectionAudit> findPathByApplId(String id) throws Exception {
        ResponseData<DetectionAudit> responseData = new ResponseData<>();
        DetectionAudit detectionAudit = this.detectionApplMapper.findPathByApplId(id);
        responseData.setData(detectionAudit);
        return responseData;

    }

    @Override
    public List<DetectionAppl> findDetectionApplsByIds(List<String> ids) {
        return this.detectionApplMapper.findDetectionApplsByIds(ids);
    }

    @Override
    public PageInfo<DetectionAppl> findNoticeBulletinByPage(DetectionAppl detectionAppl, Integer page, Integer rows) {
        if(page ==null){
            page =1;
        }
        if(rows ==null){
            rows =10;
        }
        detectionAppl.setPageNum(page);
        detectionAppl.setPageSize(rows);
        PageHelper.startPage(detectionAppl);
        Page<DetectionAppl> pg = this.detectionAuditMapper.findNoticeBulletinByPage(detectionAppl);
        return  new PageInfo<DetectionAppl>(pg);
    }

    @Override
    public ResponseData<DetectionAuditVo> findNoticeBulletinById(String id) {
        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        List<DetectionAuditVo> list = this.detectionAuditMapper.findNoticeBulletinById(id);
        if(list !=null && list.size()>0) {
            responseData.setData(list.get(0));
        }else {
            responseData.setData(null);
        }
        return responseData;
    }

}
