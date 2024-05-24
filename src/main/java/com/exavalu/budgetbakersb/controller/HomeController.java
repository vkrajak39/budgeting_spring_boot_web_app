package com.exavalu.budgetbakersb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.*;
import com.exavalu.budgetbakersb.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	UserRepository empRepo;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@GetMapping("/")
	public String index() {
		return "login";
	}
	
	
	
	@GetMapping("/error")
	public String error() {
		return "error2";
	}

	

	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}

	@GetMapping("/signin")
	public String signin() {
		return "login";
	}

	@GetMapping("/invalid")
	public String invalid() {
		return "dummy";
	}

	@GetMapping("/userlogout")
	public String logout() {
		return "logout";
	}
	
	@GetMapping("/register")
	public String registerUser()
	{
		
		return "register";
	}
		
	@PostMapping("/register")
	public String userRegistration(@ModelAttribute User emp, Model model )
	{
		
		User user= new User();
		boolean log= true;
		user.setEmail(emp.getEmail());
		user.setFirstName(emp.getFirstName());
		user.setLastName(emp.getLastName());
		user.setImagePath("images/userpic");
		user.setLoggedIn(log);
		user.setPassword(encoder.encode(emp.getPassword()));
		empRepo.save(user);
		model.addAttribute(emp);
		return "login";
	}

}