package com.safecode.cyberzone.dto;

import java.util.List;
import java.util.Map;

import com.safecode.cyberzone.base.pojo.SysUser;
import com.safecode.cyberzone.pojo.SysCorps;

public class JsonCorps<T> {
	 private boolean status =true; //结果状态，默认为true
	    private String errorMsg; //异常单数
	    private String successMsg;
	  
	    
	    private T singleDept; //返回的单数

	    private List<T> deptList; //返回集合

	    private Map<String,Object> map;//返回的map集合


	    private Map<String,Object> errorMap; //异常集合
	    
	  
	    	
	    private List<SysCorps> sysCorps;


		public List<SysCorps> getSysCorps() {
			return sysCorps;
		}


		public void setSysCorps(List<SysCorps> sysCorps) {
			this.sysCorps = sysCorps;
		}


	


		public boolean isStatus() {
			return status;
		}


		public void setStatus(boolean status) {
			this.status = status;
		}


		public String getErrorMsg() {
			return errorMsg;
		}


		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}


		public String getSuccessMsg() {
			return successMsg;
		}


		public void setSuccessMsg(String successMsg) {
			this.successMsg = successMsg;
		}


		public T getSingleDept() {
			return singleDept;
		}


		public void setSingleDept(T singleDept) {
			this.singleDept = singleDept;
		}


		public List<T> getDeptList() {
			return deptList;
		}


		public void setDeptList(List<T> deptList) {
			this.deptList = deptList;
		}


		public Map<String, Object> getMap() {
			return map;
		}


		public void setMap(Map<String, Object> map) {
			this.map = map;
		}


		public Map<String, Object> getErrorMap() {
			return errorMap;
		}


		public void setErrorMap(Map<String, Object> errorMap) {
			this.errorMap = errorMap;
		}
	    
	    
	    

}
