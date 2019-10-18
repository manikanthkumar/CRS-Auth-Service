package com.pramati.crs.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.crs.authservice.config.entity.UserProfile;
import com.pramati.crs.authservice.repository.UserProfilesRepository;

@Service
public class UserProfilesServiceImpl implements UserProfilesService {

	@Autowired
	private UserProfilesRepository userProfileRepository;

	public void createUserProfile(UserProfile userProfile) {
		userProfileRepository.save(userProfile);
	}

	public void updateUserProfile(UserProfile userProfile) {
		userProfileRepository.save(userProfile);

	}
}
