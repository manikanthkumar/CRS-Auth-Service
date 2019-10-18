package com.pramati.crs.authservice.config.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "UserProfiles")
public class UserProfile {

	@Id
	private String username;
	private String password;
	@ElementCollection(targetClass = String.class)
	private Set<String> scope;
	@ElementCollection(targetClass = GrantedAuthority.class)
	private List<GrantedAuthority> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
