package com.safecode.cyberzone.pojo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SysCorps {
    private Long id;
    private String name;
    private String logo;
    private MultipartFile logofile;
    private String describe;
    private List<SysUser> sysuser;
    private boolean delflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public MultipartFile getLogofile() {
        return logofile;
    }

    public void setLogofile(MultipartFile logofile) {
        this.logofile = logofile;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<SysUser> getSysuser() {
        return sysuser;
    }

    public void setSysuser(List<SysUser> sysuser) {
        this.sysuser = sysuser;
    }

    public boolean isDelflag() {
        return delflag;
    }

    public void setDelflag(boolean delflag) {
        this.delflag = delflag;
    }
}
