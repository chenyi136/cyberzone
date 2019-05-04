package com.safecode.cyberzone.pojo;

import java.util.Date;

public class TargetCity {
    
	private Long id; //主键id
	
    private Long dicId; //城市靶标分类id
    
    private String dicName; //城市靶标分类名称
    
    private String organizationName; //单位名称
    
    private String ipUrl; // ip地址/域名地址
    
    private String remark; //备注

    private Date createTime;//创建日期

    private Long createBy;//创建人

    private Date updateTime;//修改日期

    private Long updateBy;//修改人

    private Boolean delFlag;//删除标识

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getIpUrl() {
		return ipUrl;
	}

	public void setIpUrl(String ipUrl) {
		this.ipUrl = ipUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
    
    

}