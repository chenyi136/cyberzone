package com.safecode.cyberzone.pojo;

import java.util.Date;

public class ToolsLibraryApply {
    private Long id;

    private String toolName;

    private String name;

    private String fileName;

    private String realName;

    private String fileSuffix;

    private Long fileSize;

    private String filePath;

    private Integer classify;

    private Integer systemEnvironment;

    private Integer systemType;

    private Integer attackDefenseFlag;

    private String applyStatus;
    
    private String applyStatusName;

    private String docName;

    private String docFileName;

    private String docRealName;

    private String docFileSuffix;

    private Long docFileSize;

    private String docFilePath;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;
    
    private String updateName;//修改人

    private Date approvedTime;

    private Long approvedBy;
    
    private String approvedName;//修改人
    
    private String realPath;
    
    private String docRealPath;
    
    public String getDocRealPath() {
		return docRealPath;
	}

	public void setDocRealPath(String docRealPath) {
		this.docRealPath = docRealPath;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

    public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}

	private Boolean delFlag;

    private String intro;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName == null ? null : toolName.trim();
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

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public Integer getSystemEnvironment() {
        return systemEnvironment;
    }

    public void setSystemEnvironment(Integer systemEnvironment) {
        this.systemEnvironment = systemEnvironment;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    public Integer getAttackDefenseFlag() {
        return attackDefenseFlag;
    }

    public void setAttackDefenseFlag(Integer attackDefenseFlag) {
        this.attackDefenseFlag = attackDefenseFlag;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName == null ? null : docName.trim();
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName == null ? null : docFileName.trim();
    }

    public String getDocRealName() {
        return docRealName;
    }

    public void setDocRealName(String docRealName) {
        this.docRealName = docRealName == null ? null : docRealName.trim();
    }

    public String getDocFileSuffix() {
        return docFileSuffix;
    }

    public void setDocFileSuffix(String docFileSuffix) {
        this.docFileSuffix = docFileSuffix == null ? null : docFileSuffix.trim();
    }

    public Long getDocFileSize() {
        return docFileSize;
    }

    public void setDocFileSize(Long docFileSize) {
        this.docFileSize = docFileSize;
    }

    public String getDocFilePath() {
        return docFilePath;
    }

    public void setDocFilePath(String docFilePath) {
        this.docFilePath = docFilePath == null ? null : docFilePath.trim();
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

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplyStatusName() {
		return applyStatusName;
	}

	public void setApplyStatusName(String applyStatusName) {
		this.applyStatusName = applyStatusName;
	}

	@Override
	public String toString() {
		return "ToolsLibraryApply [id=" + id + ", toolName=" + toolName + ", name=" + name + ", fileName=" + fileName
				+ ", realName=" + realName + ", fileSuffix=" + fileSuffix + ", fileSize=" + fileSize + ", filePath="
				+ filePath + ", classify=" + classify + ", systemEnvironment=" + systemEnvironment + ", systemType="
				+ systemType + ", attackDefenseFlag=" + attackDefenseFlag + ", applyStatus=" + applyStatus
				+ ", docName=" + docName + ", docFileName=" + docFileName + ", docRealName=" + docRealName
				+ ", docFileSuffix=" + docFileSuffix + ", docFileSize=" + docFileSize + ", docFilePath=" + docFilePath
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", updateName=" + updateName + ", approvedTime=" + approvedTime + ", approvedBy="
				+ approvedBy + ", approvedName=" + approvedName + ", delFlag=" + delFlag + ", intro=" + intro
				+ ", remark=" + remark + "]";
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
        ToolsLibraryApply other = (ToolsLibraryApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getToolName() == null ? other.getToolName() == null : this.getToolName().equals(other.getToolName()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getClassify() == null ? other.getClassify() == null : this.getClassify().equals(other.getClassify()))
            && (this.getSystemEnvironment() == null ? other.getSystemEnvironment() == null : this.getSystemEnvironment().equals(other.getSystemEnvironment()))
            && (this.getSystemType() == null ? other.getSystemType() == null : this.getSystemType().equals(other.getSystemType()))
            && (this.getAttackDefenseFlag() == null ? other.getAttackDefenseFlag() == null : this.getAttackDefenseFlag().equals(other.getAttackDefenseFlag()))
            && (this.getApplyStatus() == null ? other.getApplyStatus() == null : this.getApplyStatus().equals(other.getApplyStatus()))
            && (this.getDocName() == null ? other.getDocName() == null : this.getDocName().equals(other.getDocName()))
            && (this.getDocFileName() == null ? other.getDocFileName() == null : this.getDocFileName().equals(other.getDocFileName()))
            && (this.getDocRealName() == null ? other.getDocRealName() == null : this.getDocRealName().equals(other.getDocRealName()))
            && (this.getDocFileSuffix() == null ? other.getDocFileSuffix() == null : this.getDocFileSuffix().equals(other.getDocFileSuffix()))
            && (this.getDocFileSize() == null ? other.getDocFileSize() == null : this.getDocFileSize().equals(other.getDocFileSize()))
            && (this.getDocFilePath() == null ? other.getDocFilePath() == null : this.getDocFilePath().equals(other.getDocFilePath()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getApprovedTime() == null ? other.getApprovedTime() == null : this.getApprovedTime().equals(other.getApprovedTime()))
            && (this.getApprovedBy() == null ? other.getApprovedBy() == null : this.getApprovedBy().equals(other.getApprovedBy()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getToolName() == null) ? 0 : getToolName().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getClassify() == null) ? 0 : getClassify().hashCode());
        result = prime * result + ((getSystemEnvironment() == null) ? 0 : getSystemEnvironment().hashCode());
        result = prime * result + ((getSystemType() == null) ? 0 : getSystemType().hashCode());
        result = prime * result + ((getAttackDefenseFlag() == null) ? 0 : getAttackDefenseFlag().hashCode());
        result = prime * result + ((getApplyStatus() == null) ? 0 : getApplyStatus().hashCode());
        result = prime * result + ((getDocName() == null) ? 0 : getDocName().hashCode());
        result = prime * result + ((getDocFileName() == null) ? 0 : getDocFileName().hashCode());
        result = prime * result + ((getDocRealName() == null) ? 0 : getDocRealName().hashCode());
        result = prime * result + ((getDocFileSuffix() == null) ? 0 : getDocFileSuffix().hashCode());
        result = prime * result + ((getDocFileSize() == null) ? 0 : getDocFileSize().hashCode());
        result = prime * result + ((getDocFilePath() == null) ? 0 : getDocFilePath().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getApprovedTime() == null) ? 0 : getApprovedTime().hashCode());
        result = prime * result + ((getApprovedBy() == null) ? 0 : getApprovedBy().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}