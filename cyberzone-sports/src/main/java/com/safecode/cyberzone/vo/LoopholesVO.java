package com.safecode.cyberzone.vo;

import java.util.Date;

import com.safecode.cyberzone.pojo.Loopholes;

public class LoopholesVO extends Loopholes {
	private Integer id;// 工单id
	private String userName;// 用户名
	private String name;// 战队名
	private String operationTitle;// 工单操作名称
	private String taskTitle;// 任务名称
	private Double total;// 总分
	private String infrastructureName;// 靶标名称
	private String vncUrl;// VNC路径地址
	private Date beginDate;
	private Date endDate;
	private String columnName;
	private String sort;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperationTitle() {
		return operationTitle;
	}

	public void setOperationTitle(String operationTitle) {
		this.operationTitle = operationTitle;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getInfrastructureName() {
		return infrastructureName;
	}

	public void setInfrastructureName(String infrastructureName) {
		this.infrastructureName = infrastructureName;
	}

	public String getVncUrl() {
		return vncUrl;
	}

	public void setVncUrl(String vncUrl) {
		this.vncUrl = vncUrl;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
