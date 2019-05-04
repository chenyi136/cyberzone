package com.safecode.cyberzone.pojo;

import org.springframework.web.multipart.MultipartFile;

public class ScreenInfrastructureConfig {
		private Long id;
		private String tmtext;
		private String targetname;
		private Long coordinatex;
		private Long coordinatey;
		private String photo;
		private MultipartFile logo; 
		private boolean delflag;
			
		
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String photo) {
			this.photo = photo;
		}
		public MultipartFile getLogo() {
			return logo;
		}
		public void setLogo(MultipartFile logo) {
			this.logo = logo;
		}
		public String getTmtext() {
			return tmtext;
		}
		public void setTmtext(String tmtext) {
			this.tmtext = tmtext;
		}
		public String getTargetname() {
			return targetname;
		}
		public void setTargetname(String targetname) {
			this.targetname = targetname;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		public Long getCoordinatex() {
			return coordinatex;
		}
		public void setCoordinatex(Long coordinatex) {
			this.coordinatex = coordinatex;
		}
		public Long getCoordinatey() {
			return coordinatey;
		}
		public void setCoordinatey(Long coordinatey) {
			this.coordinatey = coordinatey;
		}
		public boolean isDelflag() {
			return delflag;
		}
		public void setDelflag(boolean delflag) {
			this.delflag = delflag;
		}
		
		
}
