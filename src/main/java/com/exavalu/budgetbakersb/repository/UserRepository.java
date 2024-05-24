package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exavalu.budgetbakersb.entity.*;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
	
	
}