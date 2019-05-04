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
public class BaseJsonResponse<T>{
    /**
     * @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
    value–字段说明
    name–重写属性名字
    dataType–重写属性类型
    required–是否必填
    example–举例说明
    hidden–隐藏
     */
    @ApiModelProperty(name = "httpCode",value = "响应状态码(200:成功；500:服务器错误)")
    private Integer httpCode = HttpCodeEnum.OK.value(); //结果状态，默认为true

    @ApiModelProperty(value = "返回的对象")
    private T singleDept; //返回的单数

    @ApiModelProperty(value = "对象集合")
    private List<T> deptList; //返回集合

    private Map<String,Object> map;//返回的map集合

    @ApiModelProperty(value = "异常信息")
    private String errorMsg; //异常单数

    private Map<String,Object> errorMap; //异常集合

    @ApiModelProperty(value = "成功信息")
    private String successMsg;


    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
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
