package com.safecode.cyberzone.mapper;

import com.github.pagehelper.Page;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.vo.DetectionAuditVo;

import java.util.List;
import java.util.Map;

public interface DetectionApplMapper {
    void addDetectionAppl(DetectionAppl detectionAppl);

    DetectionAppl findDetectionApplById(String id);

    void delDetectionApplById(String id);

    void putDetectionAppl(DetectionAppl detectionApplOld);

    void saveDetectionProc(Map<String, Object> map);

    Page<DetectionAppl> findDetectionApplsByPage(DetectionAppl detectionAppl);

    Integer findDetectionApplTotal(DetectionAppl detectionAppl);

    DetectionAudit findPathByApplId(String id);

    List<DetectionAppl> findDetectionApplsByIds(List<String> ids);

    //void updateDetectionAppl(DetectionAppl detectionApplOld);

    
}
