package com.safecode.cyberzone.pojo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ScreenInfrastructureTm {
		private Long id;
		private Long tmid;
		private Long coordinatex;
		private Long coordinatey;
		private String photo;
		private MultipartFile logo; 
		private boolean delflag;
			
		
		public Long getTmid() {
			return tmid;
		}
		public void setTmid(Long tmid) {
			this.tmid = tmid;
		}
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
