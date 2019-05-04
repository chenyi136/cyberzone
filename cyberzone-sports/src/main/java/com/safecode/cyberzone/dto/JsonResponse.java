package com.safecode.cyberzone.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by xuq on 2018/6/21.
 */
public class JsonResponse<T> {




    private boolean status =true; //结果状态，默认为true
    private String errorMsg; //异常单数
    private String successMsg;
    
    private T singleDept; //返回的单数

    private List<T> deptList; //返回集合

    private Map<String,Object> map;//返回的map集合


    private Map<String,Object> errorMap; //异常集合


    public boolean isStatus() {
        return status;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, Object> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, Object> errorMap) {
        this.errorMap = errorMap;
    }

}
