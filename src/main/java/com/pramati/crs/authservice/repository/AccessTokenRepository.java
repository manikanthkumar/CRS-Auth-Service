package com.pramati.crs.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramati.crs.authservice.config.entity.OAuth2AccessTokenEntity;

@Repository
public interface AccessTokenRepository extends JpaRepository<OAuth2AccessTokenEntity, String> {

	OAuth2AccessTokenEntity findByTokenId(String tokenId);

	OAuth2AccessTokenEntity findByAuthentication(byte[] authentication);

}
