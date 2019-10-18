package com.pramati.crs.authservice.config;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.pramati.crs.authservice.config.entity.OAuth2AccessTokenEntity;
import com.pramati.crs.authservice.repository.AccessTokenRepository;

public class JpaTokenStore implements TokenStore {

	private AccessTokenRepository repository;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public JpaTokenStore(AccessTokenRepository repository) {
		this.repository = repository;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		OAuth2AccessTokenEntity entity = new OAuth2AccessTokenEntity();
		entity.setAccessToken(token);
		entity.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
		entity.setAuthentication(authentication);
		entity.setTokenId(extractTokenKey(token.getValue()));
		entity.setUserName(authentication.getOAuth2Request().getClientId());
		repository.save(entity);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessTokenEntity entity = repository.findByTokenId(extractTokenKey(tokenValue));
		if (entity != null) {
			return entity.getAccessToken();
		}
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		OAuth2AccessTokenEntity entity = repository.findByTokenId(extractTokenKey(token.getValue()));
		if (entity != null) {
			repository.delete(entity);
		}
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessTokenEntity entity = repository.findByAuthentication(SerializationUtils.serialize(authentication));
		if (entity != null) {
			return entity.getAccessToken();
		}
		return null;
	}

	public OAuth2AccessTokenEntity readOAuthEntityFromTokenId(String accessToken) {
		return repository.findByTokenId(extractTokenKey(accessToken));
	}

	public String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub

	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

}
