package com.safecode.cyberzone.vo;

/**
 * Created by xuq on 2018/6/28.
 */
public class DicTypeVo extends BaseCommonVo {

    private Integer status =1;
    private String text;
    private String code;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
