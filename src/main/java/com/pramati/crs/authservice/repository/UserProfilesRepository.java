package com.pramati.crs.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramati.crs.authservice.config.entity.UserProfile;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, String> {

}
