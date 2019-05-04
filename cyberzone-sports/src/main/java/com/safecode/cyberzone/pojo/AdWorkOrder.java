package com.safecode.cyberzone.pojo;

import java.util.Date;

public class AdWorkOrder {
    private Long id;

    private Long teamId;

    private Long taskId;

    private String taskTitle;

    private Long targetId;

    private Integer operationCategory;

    private Integer operationSubCategory;

    private Integer emergencyLevel;

    private String operationTitle;

    private Integer riskWarning;

    private Integer status;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean delFlag;

    private String riskDescription;

    private String detailedDescription;

    private String createAnneRemark;

    private String operationResult;

    private String completeWay;

    private String completeAnneRemark;

    private String archiveAnneRemark;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getOperationCategory() {
        return operationCategory;
    }

    public void setOperationCategory(Integer operationCategory) {
        this.operationCategory = operationCategory;
    }

    public Integer getOperationSubCategory() {
        return operationSubCategory;
    }

    public void setOperationSubCategory(Integer operationSubCategory) {
        this.operationSubCategory = operationSubCategory;
    }

    public Integer getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(Integer emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getOperationTitle() {
        return operationTitle;
    }

    public void setOperationTitle(String operationTitle) {
        this.operationTitle = operationTitle == null ? null : operationTitle.trim();
    }

    public Integer getRiskWarning() {
        return riskWarning;
    }

    public void setRiskWarning(Integer riskWarning) {
        this.riskWarning = riskWarning;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription == null ? null : riskDescription.trim();
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription == null ? null : detailedDescription.trim();
    }

    public String getCreateAnneRemark() {
        return createAnneRemark;
    }

    public void setCreateAnneRemark(String createAnneRemark) {
        this.createAnneRemark = createAnneRemark == null ? null : createAnneRemark.trim();
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult == null ? null : operationResult.trim();
    }

    public String getCompleteWay() {
        return completeWay;
    }

    public void setCompleteWay(String completeWay) {
        this.completeWay = completeWay == null ? null : completeWay.trim();
    }

    public String getCompleteAnneRemark() {
        return completeAnneRemark;
    }

    public void setCompleteAnneRemark(String completeAnneRemark) {
        this.completeAnneRemark = completeAnneRemark == null ? null : completeAnneRemark.trim();
    }

    public String getArchiveAnneRemark() {
        return archiveAnneRemark;
    }

    public void setArchiveAnneRemark(String archiveAnneRemark) {
        this.archiveAnneRemark = archiveAnneRemark == null ? null : archiveAnneRemark.trim();
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
        sb.append(", teamId=").append(teamId);
        sb.append(", taskId=").append(taskId);
        sb.append(", taskTitle=").append(taskTitle);
        sb.append(", targetId=").append(targetId);
        sb.append(", operationCategory=").append(operationCategory);
        sb.append(", operationSubCategory=").append(operationSubCategory);
        sb.append(", emergencyLevel=").append(emergencyLevel);
        sb.append(", operationTitle=").append(operationTitle);
        sb.append(", riskWarning=").append(riskWarning);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", riskDescription=").append(riskDescription);
        sb.append(", detailedDescription=").append(detailedDescription);
        sb.append(", createAnneRemark=").append(createAnneRemark);
        sb.append(", operationResult=").append(operationResult);
        sb.append(", completeWay=").append(completeWay);
        sb.append(", completeAnneRemark=").append(completeAnneRemark);
        sb.append(", archiveAnneRemark=").append(archiveAnneRemark);
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
        AdWorkOrder other = (AdWorkOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTeamId() == null ? other.getTeamId() == null : this.getTeamId().equals(other.getTeamId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskTitle() == null ? other.getTaskTitle() == null : this.getTaskTitle().equals(other.getTaskTitle()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getOperationCategory() == null ? other.getOperationCategory() == null : this.getOperationCategory().equals(other.getOperationCategory()))
            && (this.getOperationSubCategory() == null ? other.getOperationSubCategory() == null : this.getOperationSubCategory().equals(other.getOperationSubCategory()))
            && (this.getEmergencyLevel() == null ? other.getEmergencyLevel() == null : this.getEmergencyLevel().equals(other.getEmergencyLevel()))
            && (this.getOperationTitle() == null ? other.getOperationTitle() == null : this.getOperationTitle().equals(other.getOperationTitle()))
            && (this.getRiskWarning() == null ? other.getRiskWarning() == null : this.getRiskWarning().equals(other.getRiskWarning()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getRiskDescription() == null ? other.getRiskDescription() == null : this.getRiskDescription().equals(other.getRiskDescription()))
            && (this.getDetailedDescription() == null ? other.getDetailedDescription() == null : this.getDetailedDescription().equals(other.getDetailedDescription()))
            && (this.getCreateAnneRemark() == null ? other.getCreateAnneRemark() == null : this.getCreateAnneRemark().equals(other.getCreateAnneRemark()))
            && (this.getOperationResult() == null ? other.getOperationResult() == null : this.getOperationResult().equals(other.getOperationResult()))
            && (this.getCompleteWay() == null ? other.getCompleteWay() == null : this.getCompleteWay().equals(other.getCompleteWay()))
            && (this.getCompleteAnneRemark() == null ? other.getCompleteAnneRemark() == null : this.getCompleteAnneRemark().equals(other.getCompleteAnneRemark()))
            && (this.getArchiveAnneRemark() == null ? other.getArchiveAnneRemark() == null : this.getArchiveAnneRemark().equals(other.getArchiveAnneRemark()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTeamId() == null) ? 0 : getTeamId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskTitle() == null) ? 0 : getTaskTitle().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getOperationCategory() == null) ? 0 : getOperationCategory().hashCode());
        result = prime * result + ((getOperationSubCategory() == null) ? 0 : getOperationSubCategory().hashCode());
        result = prime * result + ((getEmergencyLevel() == null) ? 0 : getEmergencyLevel().hashCode());
        result = prime * result + ((getOperationTitle() == null) ? 0 : getOperationTitle().hashCode());
        result = prime * result + ((getRiskWarning() == null) ? 0 : getRiskWarning().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getRiskDescription() == null) ? 0 : getRiskDescription().hashCode());
        result = prime * result + ((getDetailedDescription() == null) ? 0 : getDetailedDescription().hashCode());
        result = prime * result + ((getCreateAnneRemark() == null) ? 0 : getCreateAnneRemark().hashCode());
        result = prime * result + ((getOperationResult() == null) ? 0 : getOperationResult().hashCode());
        result = prime * result + ((getCompleteWay() == null) ? 0 : getCompleteWay().hashCode());
        result = prime * result + ((getCompleteAnneRemark() == null) ? 0 : getCompleteAnneRemark().hashCode());
        result = prime * result + ((getArchiveAnneRemark() == null) ? 0 : getArchiveAnneRemark().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}