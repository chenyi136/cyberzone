package com.safecode.cyberzone.pojo;

import java.math.BigInteger;

/**
 * Created by xuq on 2018/6/21.
 */
public class DicType {
    private  Integer id;
    private  Integer status;
    private  String text;
    private  String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "DicType{" +
                "id=" + id +
                ", status=" + status +
                ", text='" + text + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
