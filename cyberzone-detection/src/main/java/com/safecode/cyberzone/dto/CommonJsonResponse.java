package com.safecode.cyberzone.dto;

import com.safecode.cyberzone.global.consts.HttpCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by xuq on 2018/7/2.
 */
@ApiModel(description = "响应的json数据")
public class CommonJsonResponse<T> {

    @ApiModelProperty(name = "httpCode",value = "响应状态码(200:成功；500:服务器错误)")
    private Integer httpCode = HttpCodeEnum.OK.value(); //结果状态，默认为true

    @ApiModelProperty(value = "返回的对象")
    private T singleDept; //返回的单数

    @ApiModelProperty(value = "对象集合")
    private List<T> deptList; //返回集合

    @ApiModelProperty(value = "异常信息")
    private String errorMsg; //异常单数

    @ApiModelProperty(value = "成功信息")
    private String successMsg;


    public Integer getHttpCode() {
        return httpCode;
    }


    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }


    public T getSingleDept() {
        return singleDept;
    }


    public List<T> getDeptList() {
        return deptList;
    }

    public void setSingleDept(T singleDept) {
        this.singleDept = singleDept;
    }

    public void setDeptList(List<T> deptList) {
        this.deptList = deptList;
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
}
