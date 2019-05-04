package com.safecode.cyberzone.pojo;

public class ScreenCorpsConfig {
		private Long id;
		private Long corpsid;
		private Long coordinatex;
		private Long coordinatey;
		private String photo;
		private boolean delflag;
		
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String photo) {
			this.photo = photo;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getCorpsid() {
			return corpsid;
		}
		public void setCorpsid(Long corpsid) {
			this.corpsid = corpsid;
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
