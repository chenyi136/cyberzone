package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.dto.CommonJsonResponse;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.vo.DetectionAuditVo;

import java.util.List;

public interface DetectionAuditMapper {
    Page<DetectionAuditVo> findDetectionAuditsByPage(DetectionAuditVo detectionAuditVo);

    void saveDetectionAudit(DetectionAudit detectionAudit);

    DetectionAudit findDetectionAuditById(String id);

    void putDetectionAudit(DetectionAudit detectionAuditOld);

    List<DetectionAudit> findDetectionAuditsByIds(List<String> ids);

    DetectionAuditVo findDetectionAuditAndAppByAuditId(String id);

    List<DetectionAuditVo> findNoticeBulletinById(String id);

    Page<DetectionAppl> findNoticeBulletinByPage(DetectionAppl detectionAppl);
}
