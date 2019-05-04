package com.safecode.cyberzone.pojo;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
	private int id;           //主键
	private int userId;       //用户id  
	private String userName;  //账户名
	private Date createTime;  //操作日期
	private String ipAddr;        //ip
	private String remark;    //操作内容
	private String projectName;
	private String requestObject; //请求参数
	private String requestUrl;//请求url
	private String method; //请求方法名
	public String getUserName() {
		return userName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(String requestObject) {
		this.requestObject = requestObject;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
}
