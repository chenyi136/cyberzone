package com.safecode.cyberzone.web.auth.holder;

import org.springframework.security.core.context.SecurityContextHolder;

import com.safecode.cyberzone.web.auth.entity.User;

public class AuthHolder {
	public static User getCurrentUser(){
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
}
