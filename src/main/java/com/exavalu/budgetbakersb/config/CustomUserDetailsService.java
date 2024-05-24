package com.exavalu.budgetbakersb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.repository.RoleRepository;
import com.exavalu.budgetbakersb.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository empRepo;

	@Autowired
	private RoleRepository roleRepository;

	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User emp = empRepo.findByEmail(email);
		String roleName = roleRepository.findRoleNameByUserId(emp.getUserid());

		if (emp == null) {
			throw new UsernameNotFoundException("user name not found");
		} else {
			return new CustomUser(emp, roleName);
		}

	}

}