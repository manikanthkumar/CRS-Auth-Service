package com.pramati.crs.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.pramati.crs.authservice.config.entity.UserProfile;
import com.pramati.crs.authservice.repository.UserProfilesRepository;

@Configuration
public class CustomClientDetailsService implements ClientDetailsService {

	@Autowired
	private UserProfilesRepository repository;

	@Override
	public ClientDetails loadClientByClientId(String clientId) {
//		Optional<UserProfile> userProfile = repository.findById(clientId);
		UserProfile user = new UserProfile();
		user.setPassword("secret");
		user.setUsername("ClientId");
		Optional<UserProfile> userProfile = Optional.of(user);

		if (userProfile.isPresent()) {
//			UserProfile user = userProfile.get();
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId(user.getUsername());
			List<String> grantTypes = new ArrayList<>();
			grantTypes.add("client_credentials");
			clientDetails.setAuthorizedGrantTypes(grantTypes);
			clientDetails.setAccessTokenValiditySeconds(10000);
			clientDetails.setRefreshTokenValiditySeconds(100000);
			clientDetails.setClientSecret(user.getPassword());
			List<String> scopes = new ArrayList<>();
			scopes.add("user_info");
			clientDetails.setScope(scopes);
			clientDetails.setAutoApproveScopes(scopes);
			List<String> authorities = new ArrayList<>();
			authorities.add("role");
			clientDetails.setAuthorities(
					AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()])));
//            clientDetails.setAdditionalInformation(details.getAdditionalInformation());
			return clientDetails;
		} else {
			throw new NoSuchClientException("No client with requested clientId: " + clientId);
		}
	}
}
