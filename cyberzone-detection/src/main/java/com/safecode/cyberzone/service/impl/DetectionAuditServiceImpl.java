package com.safecode.cyberzone.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.controller.DetectionApplController;
import com.safecode.cyberzone.global.consts.DetectionApplConsts;
import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import com.safecode.cyberzone.mapper.DetectionApplMapper;
import com.safecode.cyberzone.mapper.DetectionAuditMapper;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.base.pojo.FileInfo;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.service.DetectionAuditService;
import com.safecode.cyberzone.utils.FileUtil;
import com.safecode.cyberzone.utils.UUIDUtils;
import com.safecode.cyberzone.vo.DetectionAuditVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DetectionAuditServiceImpl implements DetectionAuditService {

    @Autowired
    private DetectionAuditMapper detectionAuditMapper;

    @Autowired
    private DetectionApplMapper detectionApplMapper;

    private static Logger log = LoggerFactory.getLogger(DetectionAuditService.class);

    @Value("${audit.file.path}")
    private String filePath;

    @Value("${audit.file.size}")
    private Integer fileSize;

    /*
     * @Description:
     * @param ${tags}
     * @return ${return_type}
     * @throws
     * @author xuq
     * @date 2018/8/15 14:51
     */
    @Override
    public PageInfo<DetectionAuditVo> findDetectionAuditsByPage(DetectionAuditVo detectionAuditVo, Integer page, Integer rows) throws  Exception {
        if(page ==null){
            page = 1;
        }
        if(rows == null){
            rows =10;
        }
//        if(!DetectionApplConsts.NO_PASS.equals(detectionAuditVo.getNoPassStatus())){
//            detectionAuditVo.setNoPassStatus(null); //如果不等于3置空
//        }

        detectionAuditVo.setPageNum(page);
        detectionAuditVo.setPageSize(rows);
        PageHelper.startPage(detectionAuditVo);
        Page<DetectionAuditVo> pg  = this.detectionAuditMapper.findDetectionAuditsByPage(detectionAuditVo);
        return  new PageInfo<DetectionAuditVo>(pg);
    }

    @Override
    public ResponseData<DetectionAuditVo> commitDedectionAudit(DetectionAuditVo detectionAuditVo, SysUser user) throws  Exception{
        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        //插入申请表数据
        DetectionAppl detectionAppl = this.detectionApplMapper.findDetectionApplById(detectionAuditVo.getApplId());

        if (detectionAppl == null){
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("根据id没有查询到申请信息！");
            return  responseData;
        }
        //检测中状态
        if(DetectionApplConsts.DETECTIONING.equals(detectionAppl.getStatus())){
            if(detectionAuditVo.getId()==null){ //往audit表里插一条数据
                DetectionAudit detectionAudit = new DetectionAudit();
                detectionAudit.setId(UUIDUtils.getUUID());
                detectionAudit.setApplId(detectionAuditVo.getApplId());
                detectionAudit.setCreateTime(new Date());
                if(user !=null){
                    detectionAudit.setCreater(user.getUserName());//创建人
                    detectionAudit.setCreaterAccount(user.getId());//创建人账号
                }
                detectionAudit.setDisagreeReason(detectionAuditVo.getDisagreeReason()); //不通过原因
                this.detectionAuditMapper.saveDetectionAudit(detectionAudit);
                //返回数据
                detectionAuditVo.setId(detectionAudit.getId());
            }else { //修改,修改更新时间等数据
                DetectionAudit detectionAuditOld = this.detectionAuditMapper.findDetectionAuditById(detectionAuditVo.getId());
                if(detectionAuditOld == null){
                    responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
                    responseData.setMsg("根据id没有查询到评测信息！");
                    return  responseData;
                }
                detectionAuditOld.setUpdateTime(new Date());
                this.detectionAuditMapper.putDetectionAudit(detectionAuditOld);
            }
        }else{
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("该申请已处理！");
            return  responseData;
        }
        //更改申请表状态
        detectionAppl.setStatus(detectionAuditVo.getStatus());
        this.detectionApplMapper.putDetectionAppl(detectionAppl);
        responseData.setData(detectionAuditVo);
        return  responseData;
    }

    @Override
    public ResponseData<DetectionAudit> confirmDetectionAudit(MultipartFile upload, DetectionAudit detectionAudit) throws  Exception{

        ResponseData<DetectionAudit> responseData = new ResponseData<>();
        if(!StringUtils.isEmpty(upload)){
            FileInfo fileInfo = null;
            DetectionAudit detectionAudit1Old = this.detectionAuditMapper.findDetectionAuditById(detectionAudit.getId());
            if(upload.getSize()>fileSize*1024*1024){
                responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
                responseData.setMsg("超出上传文件的限制范围"+fileSize+"Mb");
                return responseData;
            }
            String filename = "/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            String path = filePath +filename;
            fileInfo = FileUtil.saveFile(upload, path);
            //设置文件路径
            detectionAudit1Old.setPath(fileInfo.getFilePath());
            //设置文件名称
            detectionAudit1Old.setAuditFileName(fileInfo.getFileName());
            //设置文件大小
            detectionAudit1Old.setAuditFileSize(fileInfo.getFileSize());
            //设置文件后缀
            // detectionAudit1Old.set(fileInfo.getFileSuffix());
            //设置存储的文件名称
            detectionAudit1Old.setAuditStorgeFileName(fileInfo.getRealName());

            //设置更新时间
            detectionAudit1Old.setUpdateTime(new Date());

            //更新
            this.detectionAuditMapper.putDetectionAudit(detectionAudit1Old);

            //更改申请表状态
            DetectionAppl detectionApplOld = this.detectionApplMapper.findDetectionApplById(detectionAudit1Old.getApplId());
            detectionApplOld.setStatus(DetectionApplConsts.END_CHECK);
            this.detectionApplMapper.putDetectionAppl(detectionApplOld);
            responseData.setData(detectionAudit1Old);

        }
        return responseData;
    }

    @Override
    public List<DetectionAudit> findDetectionAuditsByIds(List<String> ids) {
        return this.detectionAuditMapper.findDetectionAuditsByIds(ids);
    }

    @Override
    public ResponseData<DetectionAuditVo> findDetectionAuditAndAppByAuditId(String id) {
        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        DetectionAuditVo detectionAuditVo = this.detectionAuditMapper.findDetectionAuditAndAppByAuditId(id);
        responseData.setData(detectionAuditVo);
        return responseData;
    }

    @Override
    public ResponseData<DetectionAuditVo> safetyCheck(DetectionAuditVo detectionAuditVo) throws  Exception{
        ResponseData<DetectionAuditVo> responseData = new ResponseData<>();
        DetectionAppl detectionAppl = this.detectionApplMapper.findDetectionApplById(detectionAuditVo.getApplId());
        if(detectionAppl == null){
            log.error("根据id没有查询到测评申请的相关信息"+detectionAuditVo.getId(),this.getClass().getName());
            responseData.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.value());
            responseData.setMsg("没有查询到测评申请的相关信息！");
            return  responseData;
        }
        //只要当检测通过后，才可以进行安全性检测
        if(DetectionApplConsts.YES_PASS.equals(detectionAppl.getStatus())){

            String safeCheckStr = detectionAppl.getSafetyCheck();
            //判断数据库是否还未进行任何检测
            if("0".equals(safeCheckStr) || StringUtils.isEmpty(safeCheckStr)){
                detectionAppl.setSafetyCheck(detectionAuditVo.getSafetyCheck());
            }else {
                //判断是否已经进行过相关检测
                String [] safeCheckList =  safeCheckStr.split("、");
                Boolean wasSafeCheck = true;
                safe:for(String s :safeCheckList){
                    //如果已经检测了
                    if(s.equals(detectionAuditVo.getSafetyCheck())){
                        wasSafeCheck = false;
                        break safe;
                    }
                }
                if(wasSafeCheck){
                    //将数据库中的检测状态和现有检测状态拼接一块放入数据库中
                    detectionAppl.setSafetyCheck(detectionAppl.getSafetyCheck()+DetectionApplConsts.SEPARATOR_SAFECHECK +detectionAuditVo.getSafetyCheck());
                }
            }
            detectionAppl.setUpdateTime(new Date());//设置更新时间
            this.detectionApplMapper.putDetectionAppl(detectionAppl);
        }
        responseData.setData(detectionAuditVo);
        return  responseData;
    }

}
