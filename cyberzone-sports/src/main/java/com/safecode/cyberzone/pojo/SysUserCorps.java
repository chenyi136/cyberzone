package com.safecode.cyberzone.pojo;



public class SysUserCorps {
    private Long id;
    private Long userid;
    private Long corpsid;
    private String identity;
    private boolean delflag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getCorpsid() {
		return corpsid;
	}
	public void setCorpsid(Long corpsid) {
		this.corpsid = corpsid;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public boolean isDelflag() {
		return delflag;
	}
	public void setDelflag(boolean delflag) {
		this.delflag = delflag;
	}
    
    
}
