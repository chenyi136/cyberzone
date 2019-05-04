package com.safecode.cyberzone.web.contants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpContants {

	public static <T> HttpEntity<T> setEntity(T t) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<T> entity = new HttpEntity<T>(t, headers);
		return entity;
	}

}
