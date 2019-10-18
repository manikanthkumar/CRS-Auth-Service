package com.pramati.crs.authservice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import com.pramati.crs.authservice.config.JpaTokenStore;
import com.pramati.crs.authservice.config.entity.OAuth2AccessTokenEntity;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private JpaTokenStore tokenStore;

	@Autowired
	private AccessTokenConverter accessTokenConverter;

	@Override
	public boolean userLogout(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		boolean revokeStatus = false;
		if (authorization != null && authorization.contains("Bearer")) {
			String tokenId = authorization.substring("Bearer".length() + 1);
			revokeStatus = tokenServices.revokeToken(tokenId);
		}
		return revokeStatus;
	}

	@Override
	public Map<String, ?> checkToken(String accessToken) {
		OAuth2AccessTokenEntity entity = tokenStore.readOAuthEntityFromTokenId(accessToken);
		if (entity == null || entity.getAccessToken() == null) {
			throw new InvalidTokenException("Token was not recognised");
		}

		if (entity.getAccessToken().isExpired()) {
			throw new InvalidTokenException("Token has expired");
		}

		OAuth2Authentication authentication = entity.getAuthentication();

		return accessTokenConverter.convertAccessToken(entity.getAccessToken(), authentication);
	}

}
