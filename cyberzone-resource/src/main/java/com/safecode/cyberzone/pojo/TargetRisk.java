package com.safecode.cyberzone.pojo;

import java.util.Date;

public class TargetRisk {
    private Long id;

    private Long infrastructureId;

    private Integer riskClassify;

    private Integer serverity;

    private String riskDescr;

    private String attackMethod;

    private String defenseMethod;

    private Integer produceEnvironment;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean delFlag;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfrastructureId() {
        return infrastructureId;
    }

    public void setInfrastructureId(Long infrastructureId) {
        this.infrastructureId = infrastructureId;
    }

    public Integer getRiskClassify() {
        return riskClassify;
    }

    public void setRiskClassify(Integer riskClassify) {
        this.riskClassify = riskClassify;
    }

    public Integer getServerity() {
        return serverity;
    }

    public void setServerity(Integer serverity) {
        this.serverity = serverity;
    }

    public String getRiskDescr() {
        return riskDescr;
    }

    public void setRiskDescr(String riskDescr) {
        this.riskDescr = riskDescr == null ? null : riskDescr.trim();
    }

    public String getAttackMethod() {
        return attackMethod;
    }

    public void setAttackMethod(String attackMethod) {
        this.attackMethod = attackMethod == null ? null : attackMethod.trim();
    }

    public String getDefenseMethod() {
        return defenseMethod;
    }

    public void setDefenseMethod(String defenseMethod) {
        this.defenseMethod = defenseMethod == null ? null : defenseMethod.trim();
    }

    public Integer getProduceEnvironment() {
        return produceEnvironment;
    }

    public void setProduceEnvironment(Integer produceEnvironment) {
        this.produceEnvironment = produceEnvironment;
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
        sb.append(", infrastructureId=").append(infrastructureId);
        sb.append(", riskClassify=").append(riskClassify);
        sb.append(", serverity=").append(serverity);
        sb.append(", riskDescr=").append(riskDescr);
        sb.append(", attackMethod=").append(attackMethod);
        sb.append(", defenseMethod=").append(defenseMethod);
        sb.append(", produceEnvironment=").append(produceEnvironment);
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
        TargetRisk other = (TargetRisk) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInfrastructureId() == null ? other.getInfrastructureId() == null : this.getInfrastructureId().equals(other.getInfrastructureId()))
            && (this.getRiskClassify() == null ? other.getRiskClassify() == null : this.getRiskClassify().equals(other.getRiskClassify()))
            && (this.getServerity() == null ? other.getServerity() == null : this.getServerity().equals(other.getServerity()))
            && (this.getRiskDescr() == null ? other.getRiskDescr() == null : this.getRiskDescr().equals(other.getRiskDescr()))
            && (this.getAttackMethod() == null ? other.getAttackMethod() == null : this.getAttackMethod().equals(other.getAttackMethod()))
            && (this.getDefenseMethod() == null ? other.getDefenseMethod() == null : this.getDefenseMethod().equals(other.getDefenseMethod()))
            && (this.getProduceEnvironment() == null ? other.getProduceEnvironment() == null : this.getProduceEnvironment().equals(other.getProduceEnvironment()))
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
        result = prime * result + ((getInfrastructureId() == null) ? 0 : getInfrastructureId().hashCode());
        result = prime * result + ((getRiskClassify() == null) ? 0 : getRiskClassify().hashCode());
        result = prime * result + ((getServerity() == null) ? 0 : getServerity().hashCode());
        result = prime * result + ((getRiskDescr() == null) ? 0 : getRiskDescr().hashCode());
        result = prime * result + ((getAttackMethod() == null) ? 0 : getAttackMethod().hashCode());
        result = prime * result + ((getDefenseMethod() == null) ? 0 : getDefenseMethod().hashCode());
        result = prime * result + ((getProduceEnvironment() == null) ? 0 : getProduceEnvironment().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}