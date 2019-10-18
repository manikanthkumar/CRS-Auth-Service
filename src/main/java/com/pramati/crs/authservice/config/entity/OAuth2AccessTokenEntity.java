package com.pramati.crs.authservice.config.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Entity
@Table(name = "OAuth2AccessTokenEntity")
public class OAuth2AccessTokenEntity {

	@Id
	private String authenticationId;
	private byte[] accessToken;
	private String userName;
	private byte[] authentication;
	private String tokenId;

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public OAuth2AccessToken getAccessToken() {
		return deserializeAccessToken(accessToken);
	}

	public void setAccessToken(OAuth2AccessToken accessToken) {
		this.accessToken = serializeAccessToken(accessToken);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = serializeAuthentication(authentication);
	}

	public OAuth2Authentication getAuthentication() {
		return deserializeAuthentication(authentication);
	}

	protected byte[] serializeAccessToken(OAuth2AccessToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
		return SerializationUtils.serialize(authentication);
	}

	protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
		return SerializationUtils.deserialize(authentication);
	}

}
