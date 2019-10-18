package com.pramati.crs.authservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.crs.authservice.config.entity.UserProfile;
import com.pramati.crs.authservice.service.UserProfilesService;

@RestController
@RequestMapping("/users")
public class UserProfileResource {

	@Autowired
	private UserProfilesService userProfileService;

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public String createUserProfiles(@RequestBody UserProfile userProfile) {
		userProfileService.createUserProfile(userProfile);
		return "UserProfie added Successfully";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public String updateUserProfiles(@RequestBody UserProfile userProfile) {
		userProfileService.updateUserProfile(userProfile);
		return "UserProfie added Successfully";
	}
}
