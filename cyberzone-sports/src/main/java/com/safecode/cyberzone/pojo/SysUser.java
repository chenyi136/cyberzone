package com.safecode.cyberzone.pojo;

import java.util.Date;
import java.util.Objects;

public class SysUser {
    private Long id;

    private String account;

    private String password;

    private String userName;

    private String phone;

    private String email;

    private String avatar;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean delFlag;

    private String faceId;

    private Integer facePerm;

    private String signature;

    private String remark;
    

    //业务字段
    private Integer faceIsLogin;//是否已经人脸登录
    private String identiyt;//队员还是队长

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUser = (SysUser) o;
        return Objects.equals(id, sysUser.id) &&
                Objects.equals(account, sysUser.account) &&
                Objects.equals(password, sysUser.password) &&
                Objects.equals(userName, sysUser.userName) &&
                Objects.equals(phone, sysUser.phone) &&
                Objects.equals(email, sysUser.email) &&
                Objects.equals(avatar, sysUser.avatar) &&
                Objects.equals(createTime, sysUser.createTime) &&
                Objects.equals(createBy, sysUser.createBy) &&
                Objects.equals(updateTime, sysUser.updateTime) &&
                Objects.equals(updateBy, sysUser.updateBy) &&
                Objects.equals(delFlag, sysUser.delFlag) &&
                Objects.equals(faceId, sysUser.faceId) &&
                Objects.equals(facePerm, sysUser.facePerm) &&
                Objects.equals(signature, sysUser.signature) &&
                Objects.equals(remark, sysUser.remark) &&
                Objects.equals(faceIsLogin, sysUser.faceIsLogin) &&
                Objects.equals(identiyt, sysUser.identiyt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, password, userName, phone, email, avatar, createTime, createBy, updateTime, updateBy, delFlag, faceId, facePerm, signature, remark, faceIsLogin, identiyt);
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", updateTime=" + updateTime +
                ", updateBy=" + updateBy +
                ", delFlag=" + delFlag +
                ", faceId='" + faceId + '\'' +
                ", facePerm=" + facePerm +
                ", signature='" + signature + '\'' +
                ", remark='" + remark + '\'' +
                ", faceIsLogin=" + faceIsLogin +
                ", identiyt='" + identiyt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getFacePerm() {
        return facePerm;
    }

    public void setFacePerm(Integer facePerm) {
        this.facePerm = facePerm;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFaceIsLogin() {
        return faceIsLogin;
    }

    public void setFaceIsLogin(Integer faceIsLogin) {
        this.faceIsLogin = faceIsLogin;
    }

    public String getIdentiyt() {
        return identiyt;
    }

    public void setIdentiyt(String identiyt) {
        this.identiyt = identiyt;
    }
}