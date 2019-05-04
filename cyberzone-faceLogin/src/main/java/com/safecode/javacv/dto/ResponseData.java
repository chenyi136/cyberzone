package com.safecode.javacv.dto;
/**
 * Spring controller中统一返回的json实体类
 * @author Lunan
 *
 * @param <T>
 */
public class ResponseData<T> {

    private Integer code;
    private String msg;
    private T data;

    public ResponseData() {

    }
    public ResponseData(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "ResponseData [code=" + code + ", msg=" + msg + ", data=" + data.toString() + "]";
    }

}
