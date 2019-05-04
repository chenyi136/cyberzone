package com.safecode.cyberzone.vo;

public class TargetInfrastructureVo {
    private String infrastructureName;
    private Integer pageNum;
	private Integer pageSize;
	public String getInfrastructureName() {
		return infrastructureName;
	}
	public void setInfrastructureName(String infrastructureName) {
		this.infrastructureName = infrastructureName;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "TargetInfrastructureVo [infrastructureName=" + infrastructureName + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infrastructureName == null) ? 0 : infrastructureName.hashCode());
		result = prime * result + ((pageNum == null) ? 0 : pageNum.hashCode());
		result = prime * result + ((pageSize == null) ? 0 : pageSize.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TargetInfrastructureVo other = (TargetInfrastructureVo) obj;
		if (infrastructureName == null) {
			if (other.infrastructureName != null)
				return false;
		} else if (!infrastructureName.equals(other.infrastructureName))
			return false;
		if (pageNum == null) {
			if (other.pageNum != null)
				return false;
		} else if (!pageNum.equals(other.pageNum))
			return false;
		if (pageSize == null) {
			if (other.pageSize != null)
				return false;
		} else if (!pageSize.equals(other.pageSize))
			return false;
		return true;
	}
	
}
