package com.safecode.cyberzone.pojo;

import java.util.Date;

public class DetectionAudit {

    private String id;
    private String path;
    private String creater;
    private Date createTime;
    private String disagreeReason;
    private Long auditFileSize;
    private String auditFileName;
    private Date updateTime;
    private Long createrAccount;
    private String applId;
    private String AuditStorgeFileName;

    public String getAuditStorgeFileName() {
        return AuditStorgeFileName;
    }

    public void setAuditStorgeFileName(String auditStorgeFileName) {
        AuditStorgeFileName = auditStorgeFileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    @Override
    public String toString() {
        return "DetectionAudit{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", creater='" + creater + '\'' +
                ", createTime=" + createTime +
                ", disagreeReason='" + disagreeReason + '\'' +
                ", auditFileSize=" + auditFileSize +
                ", auditFileName='" + auditFileName + '\'' +
                ", updateTime=" + updateTime +
                ", createrAccount='" + createrAccount + '\'' +
                ", applId=" + applId +
                ", AuditStorgeFileName='" + AuditStorgeFileName + '\'' +
                '}';
    }
}
