package com.safecode.cyberzone.web.auth.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safecode.cyberzone.base.dto.ResponseData;

@Component("expiredSessionStrategy")
public class ExpiredSessionStrategy implements SessionInformationExpiredStrategy {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		@SuppressWarnings("rawtypes")
		ResponseData data = new ResponseData();

		data.setCode(HttpStatus.UNAUTHORIZED.value());
		data.setMsg("并发登录，此账号已在其他设备登录");
		event.getResponse().setContentType("application/json;charset=UTF-8");
		event.getResponse().getWriter().write(objectMapper.writeValueAsString(data));
	}

}
