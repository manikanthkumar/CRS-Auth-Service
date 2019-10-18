package com.pramati.crs.authservice.service;

import com.pramati.crs.authservice.config.entity.UserProfile;

public interface UserProfilesService {

	public void createUserProfile(UserProfile userProfile);

	public void updateUserProfile(UserProfile userProfile);
}
