package com.safecode.cyberzone.service;

import com.github.pagehelper.PageInfo;
import com.safecode.cyberzone.base.dto.ResponseData;
import com.safecode.cyberzone.pojo.DetectionAppl;
import com.safecode.cyberzone.pojo.DetectionAudit;
import com.safecode.cyberzone.vo.DetectionAuditVo;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DetectionApplService {
    ResponseData<DetectionAppl> addDetectionAppl(DetectionAppl detectionAppl, MultipartFile upload) throws IOException;

    ResponseData<DetectionAppl> delDetectionAppl(String id) throws Exception;

    PageInfo<DetectionAppl> findDetectionApplsByPage(DetectionAppl detectionAppl, Integer page, Integer rows);

    ResponseData<List<DetectionAppl>> findDetectionAppls(DetectionAppl detectionAppl);

    ResponseData<Integer> findDetectionApplTotal(DetectionAppl detectionAppl) throws Exception;

    ResponseData<DetectionAppl> findDetectionApplById(String id);

    ResponseData<DetectionAppl> putDetectionAppl(DetectionAppl detectionAppl, MultipartFile upload) throws IOException, Exception;

    ResponseData<DetectionAppl> beginDetectionAppl(DetectionAppl detectionAppl);

    ResponseData<DetectionAudit> findPathByApplId(String id) throws Exception;

    List<DetectionAppl> findDetectionApplsByIds(List<String> ids);
    
    PageInfo<DetectionAppl> findNoticeBulletinByPage(DetectionAppl detectionAppl, Integer page, Integer rows);

    ResponseData<DetectionAuditVo> findNoticeBulletinById(String id);
    
}
