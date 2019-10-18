package com.pramati.crs.authservice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

	boolean userLogout(HttpServletRequest request);
	
	Map<String, ?> checkToken(String accessToken);
}
