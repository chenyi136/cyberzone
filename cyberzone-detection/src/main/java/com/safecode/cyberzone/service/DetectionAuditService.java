package com.safecode.cyberzone.service;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.vo.DetectionAuditVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DetectionAuditService {
    PageInfo<DetectionAuditVo> findDetectionAuditsByPage(DetectionAuditVo detectionAuditVo, Integer page, Integer rows) throws Exception;

    ResponseData<DetectionAuditVo> commitDedectionAudit(DetectionAuditVo detectionAuditVo, SysUser user) throws Exception;

    ResponseData<DetectionAudit> confirmDetectionAudit(MultipartFile upload, DetectionAudit detectionAudit) throws Exception;

    List<DetectionAudit> findDetectionAuditsByIds(List<String> ids);

    ResponseData<DetectionAuditVo> findDetectionAuditAndAppByAuditId(String id);

    ResponseData<DetectionAuditVo> safetyCheck(DetectionAuditVo detectionAuditVo) throws Exception;

}
