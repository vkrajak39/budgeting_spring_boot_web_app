package com.exavalu.budgetbakersb.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.exavalu.budgetbakersb.entity.User;

public class CustomUser implements UserDetails {

	/**
	 * 
	 */
	// Assuming you inject RoleRepository

	private static final long serialVersionUID = 1L;
	private User emp;
	private String roleName;

	public CustomUser(User user, String roleName) {
		super();
		this.emp = user;
		this.roleName = roleName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
		System.out.println(authority);
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return emp.getPassword();
	}

	@Override
	public String getUsername() {
		return emp.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getUserid() {
		return emp.getUserid();
	}

	public User getUser() {
		return emp;
	}

	

}