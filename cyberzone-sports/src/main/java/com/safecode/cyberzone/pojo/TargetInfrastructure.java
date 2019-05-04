package com.safecode.cyberzone.pojo;

import java.util.Date;

public class TargetInfrastructure {
    private Long id;
    
    private Long dicid;

    private String infrastructureName;

    private String vncUrl;

    private String name;

    private String fileName;

    private String realName;

    private String fileSuffix;

    private Long fileSize;

    private String filePath;

    private String vncName;

    private String vncFileName;

    private String vncRealName;

    private String vncFileSuffix;

    private Long vncFileSize;

    private String vncFilePath;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean delFlag;

    private String remark;

    public Long getDicid() {
		return dicid;
	}

	public void setDicid(Long dicid) {
		this.dicid = dicid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfrastructureName() {
        return infrastructureName;
    }

    public void setInfrastructureName(String infrastructureName) {
        this.infrastructureName = infrastructureName == null ? null : infrastructureName.trim();
    }

    public String getVncUrl() {
        return vncUrl;
    }

    public void setVncUrl(String vncUrl) {
        this.vncUrl = vncUrl == null ? null : vncUrl.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getVncName() {
        return vncName;
    }

    public void setVncName(String vncName) {
        this.vncName = vncName == null ? null : vncName.trim();
    }

    public String getVncFileName() {
        return vncFileName;
    }

    public void setVncFileName(String vncFileName) {
        this.vncFileName = vncFileName == null ? null : vncFileName.trim();
    }

    public String getVncRealName() {
        return vncRealName;
    }

    public void setVncRealName(String vncRealName) {
        this.vncRealName = vncRealName == null ? null : vncRealName.trim();
    }

    public String getVncFileSuffix() {
        return vncFileSuffix;
    }

    public void setVncFileSuffix(String vncFileSuffix) {
        this.vncFileSuffix = vncFileSuffix == null ? null : vncFileSuffix.trim();
    }

    public Long getVncFileSize() {
        return vncFileSize;
    }

    public void setVncFileSize(Long vncFileSize) {
        this.vncFileSize = vncFileSize;
    }

    public String getVncFilePath() {
        return vncFilePath;
    }

    public void setVncFilePath(String vncFilePath) {
        this.vncFilePath = vncFilePath == null ? null : vncFilePath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", infrastructureName=").append(infrastructureName);
        sb.append(", vncUrl=").append(vncUrl);
        sb.append(", name=").append(name);
        sb.append(", fileName=").append(fileName);
        sb.append(", realName=").append(realName);
        sb.append(", fileSuffix=").append(fileSuffix);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", filePath=").append(filePath);
        sb.append(", vncName=").append(vncName);
        sb.append(", vncFileName=").append(vncFileName);
        sb.append(", vncRealName=").append(vncRealName);
        sb.append(", vncFileSuffix=").append(vncFileSuffix);
        sb.append(", vncFileSize=").append(vncFileSize);
        sb.append(", vncFilePath=").append(vncFilePath);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TargetInfrastructure other = (TargetInfrastructure) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInfrastructureName() == null ? other.getInfrastructureName() == null : this.getInfrastructureName().equals(other.getInfrastructureName()))
            && (this.getVncUrl() == null ? other.getVncUrl() == null : this.getVncUrl().equals(other.getVncUrl()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getVncName() == null ? other.getVncName() == null : this.getVncName().equals(other.getVncName()))
            && (this.getVncFileName() == null ? other.getVncFileName() == null : this.getVncFileName().equals(other.getVncFileName()))
            && (this.getVncRealName() == null ? other.getVncRealName() == null : this.getVncRealName().equals(other.getVncRealName()))
            && (this.getVncFileSuffix() == null ? other.getVncFileSuffix() == null : this.getVncFileSuffix().equals(other.getVncFileSuffix()))
            && (this.getVncFileSize() == null ? other.getVncFileSize() == null : this.getVncFileSize().equals(other.getVncFileSize()))
            && (this.getVncFilePath() == null ? other.getVncFilePath() == null : this.getVncFilePath().equals(other.getVncFilePath()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInfrastructureName() == null) ? 0 : getInfrastructureName().hashCode());
        result = prime * result + ((getVncUrl() == null) ? 0 : getVncUrl().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getVncName() == null) ? 0 : getVncName().hashCode());
        result = prime * result + ((getVncFileName() == null) ? 0 : getVncFileName().hashCode());
        result = prime * result + ((getVncRealName() == null) ? 0 : getVncRealName().hashCode());
        result = prime * result + ((getVncFileSuffix() == null) ? 0 : getVncFileSuffix().hashCode());
        result = prime * result + ((getVncFileSize() == null) ? 0 : getVncFileSize().hashCode());
        result = prime * result + ((getVncFilePath() == null) ? 0 : getVncFilePath().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}