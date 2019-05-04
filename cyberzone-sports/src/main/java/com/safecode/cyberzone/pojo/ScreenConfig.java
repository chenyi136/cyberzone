package com.safecode.cyberzone.pojo;

import org.springframework.web.multipart.MultipartFile;

public class ScreenConfig {
		private Long id;
		private String name; 
		private String backgroundphoto;
		private MultipartFile logo;
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
		public String getBackgroundphoto() {
			return backgroundphoto;
		}
		public void setBackgroundphoto(String backgroundphoto) {
			this.backgroundphoto = backgroundphoto;
		}
		public MultipartFile getLogo() {
			return logo;
		}
		public void setLogo(MultipartFile logo) {
			this.logo = logo;
		}
		public boolean isDelflag() {
			return delflag;
		}
		public void setDelflag(boolean delflag) {
			this.delflag = delflag;
		}
		
		
}
