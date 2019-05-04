package com.safecode.cyberzone.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ScreenInfrastructure {
	private Long id;
	private String tmtext;
	private List<String> targetname ;
	private Long coordinatex;
	private Long coordinatey;
	private String photo;
	private MultipartFile logo; 
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getTmtext() {
		return tmtext;
	}
	public void setTmtext(String tmtext) {
		this.tmtext = tmtext;
	}
	
	public List<String> getTargetname() {
		return targetname;
	}
	public void setTargetname(List<String> targetname) {
		this.targetname = targetname;
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
	
	
}
