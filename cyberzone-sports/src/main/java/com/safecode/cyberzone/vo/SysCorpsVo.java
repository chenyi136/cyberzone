package com.safecode.cyberzone.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.safecode.cyberzone.pojo.SysUser;

public class SysCorpsVo {
	 private Long id;
	    private String name;
	    private String logo;
	    private MultipartFile logofile;
	    private String describe;
	    private boolean delflag;
	    
	    private List<SysUser> sysuser;

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

		public boolean isDelflag() {
			return delflag;
		}

		public void setDelflag(boolean delflag) {
			this.delflag = delflag;
		}

		public List<SysUser> getSysuser() {
			return sysuser;
		}

		public void setSysuser(List<SysUser> sysuser) {
			this.sysuser = sysuser;
		}
	  
	    
	    
	    
}
