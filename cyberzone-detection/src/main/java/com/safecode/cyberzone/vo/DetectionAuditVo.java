package com.safecode.cyberzone.vo;

import com.safecode.cyberzone.pojo.BasePojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetectionAuditVo extends BasePojo {

    private String id;
    private String taskName;
    private String taskDescription;
    private String userName; //用户名称
    private String userEmail; //邮箱
    private String companyName; //公司名称
    private String companyStation; //公司地址
    private String userTelephone;  //电话
    private String userDescription;  //备注
    private String deviceName;  //设备名称(包括软件、漏洞、芯片、硬件)
    private String deviceDescription;//设备描述
    private String status; //状态
    private String safetyCheck; //安全性检测
    private String statusName;
    private String type;
    private String url;
    private String applPath;  //文件路径
    private String applRealFileName; //文件名称
    private Long applFileSize; //文件大小
    private String applFileSuffix;//文件后缀
    private String applStorgeFileName;//存储文件名
    private String creater;
    private Long createrAccount;
    private Date createTime;
    private String path;
    private String disagreeReason;
    private Long auditFileSize;
    private String auditFileName;
    private String applId;
    private String noPassStatus; //是否需要不通过的数据
    private List<String> ids = new ArrayList<>();

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyStation() {
        return companyStation;
    }

    public void setCompanyStation(String companyStation) {
        this.companyStation = companyStation;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSafetyCheck() {
        return safetyCheck;
    }

    public void setSafetyCheck(String safetyCheck) {
        this.safetyCheck = safetyCheck;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApplPath() {
        return applPath;
    }

    public void setApplPath(String applPath) {
        this.applPath = applPath;
    }

    public String getApplRealFileName() {
        return applRealFileName;
    }

    public void setApplRealFileName(String applRealFileName) {
        this.applRealFileName = applRealFileName;
    }

    public Long getApplFileSize() {
        return applFileSize;
    }

    public void setApplFileSize(Long applFileSize) {
        this.applFileSize = applFileSize;
    }

    public String getApplFileSuffix() {
        return applFileSuffix;
    }

    public void setApplFileSuffix(String applFileSuffix) {
        this.applFileSuffix = applFileSuffix;
    }

    public String getApplStorgeFileName() {
        return applStorgeFileName;
    }

    public void setApplStorgeFileName(String applStorgeFileName) {
        this.applStorgeFileName = applStorgeFileName;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Long getCreaterAccount() {
        return createrAccount;
    }

    public void setCreaterAccount(Long createrAccount) {
        this.createrAccount = createrAccount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisagreeReason() {
        return disagreeReason;
    }

    public void setDisagreeReason(String disagreeReason) {
        this.disagreeReason = disagreeReason;
    }

    public Long getAuditFileSize() {
        return auditFileSize;
    }

    public void setAuditFileSize(Long auditFileSize) {
        this.auditFileSize = auditFileSize;
    }

    public String getAuditFileName() {
        return auditFileName;
    }

    public void setAuditFileName(String auditFileName) {
        this.auditFileName = auditFileName;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getNoPassStatus() {
        return noPassStatus;
    }

    public void setNoPassStatus(String noPassStatus) {
        this.noPassStatus = noPassStatus;
    }

    @Override
    public String toString() {
        return "DetectionAuditVo{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyStation='" + companyStation + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceDescription='" + deviceDescription + '\'' +
                ", status='" + status + '\'' +
                ", safetyCheck='" + safetyCheck + '\'' +
                ", statusName='" + statusName + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", applPath='" + applPath + '\'' +
                ", applRealFileName='" + applRealFileName + '\'' +
                ", applFileSize=" + applFileSize +
                ", applFileSuffix='" + applFileSuffix + '\'' +
                ", applStorgeFileName='" + applStorgeFileName + '\'' +
                ", creater='" + creater + '\'' +
                ", createrAccount='" + createrAccount + '\'' +
                ", createTime=" + createTime +
                ", path='" + path + '\'' +
                ", disagreeReason='" + disagreeReason + '\'' +
                ", auditFileSize=" + auditFileSize +
                ", auditFileName='" + auditFileName + '\'' +
                ", applId=" + applId +
                ", noPassStatus='" + noPassStatus + '\'' +
                '}';
    }
}
