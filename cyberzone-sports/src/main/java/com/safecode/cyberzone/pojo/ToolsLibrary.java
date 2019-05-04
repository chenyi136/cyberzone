package com.safecode.cyberzone.pojo;

import java.util.Date;

public class ToolsLibrary {
    private Long id;//主键id

    private String toolName;//工具名称

    private String name;//文件名称(无后缀)

    private String fileName;//文件名称(有后缀)

    private String realName;//存储名称(处理后)

    private String fileSuffix;//文件后缀

    private Long fileSize;//文件大小

    private String filePath;//存储路径

    private Integer classify;//所属分类
    
    private String classifyName;

    private Integer systemEnvironment;//操作系统
    
    private String systemEnvironmentName;

    private Integer systemType;//系统类型
    
    public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getSystemEnvironmentName() {
		return systemEnvironmentName;
	}

	public void setSystemEnvironmentName(String systemEnvironmentName) {
		this.systemEnvironmentName = systemEnvironmentName;
	}

	public String getSystemTypeName() {
		return systemTypeName;
	}

	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}

	public String getAttackDefenseFlagName() {
		return attackDefenseFlagName;
	}

	public void setAttackDefenseFlagName(String attackDefenseFlagName) {
		this.attackDefenseFlagName = attackDefenseFlagName;
	}

	private String systemTypeName;

    private Integer attackDefenseFlag;//攻防标识
    
    private String attackDefenseFlagName;

    private String docName;//说明文档名称(无后缀)

    private String docFileName;//说明文档名称(有后缀)

    private String docRealName;//说明文档存储名称(处理后)

    private String docFileSuffix;//说明文档后缀

    private Long docFileSize;//说明文档大小

    private String docFilePath;//说明文档存储路径

    private Date createTime;//创建日期

    private Long createBy;//创建人

    private Date updateTime;//修改日期

    private Long updateBy;//修改人

    private Boolean delFlag;//删除标识

    private String intro;//简介

    private String remark;//备注
    
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", toolName=").append(toolName);
        sb.append(", name=").append(name);
        sb.append(", fileName=").append(fileName);
        sb.append(", realName=").append(realName);
        sb.append(", fileSuffix=").append(fileSuffix);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", filePath=").append(filePath);
        sb.append(", classify=").append(classify);
        sb.append(", systemEnvironment=").append(systemEnvironment);
        sb.append(", systemType=").append(systemType);
        sb.append(", attackDefenseFlag=").append(attackDefenseFlag);
        sb.append(", docName=").append(docName);
        sb.append(", docFileName=").append(docFileName);
        sb.append(", docRealName=").append(docRealName);
        sb.append(", docFileSuffix=").append(docFileSuffix);
        sb.append(", docFileSize=").append(docFileSize);
        sb.append(", docFilePath=").append(docFilePath);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", intro=").append(intro);
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
        ToolsLibrary other = (ToolsLibrary) that;
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
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}