package com.exavalu.budgetbakersb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.entity.Menu;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.services.AccountService;
import com.exavalu.budgetbakersb.services.AccountService2;
import com.exavalu.budgetbakersb.services.MenuService;
import com.exavalu.budgetbakersb.services.RecordService2;
import com.exavalu.budgetbakersb.services.RecordServices;
import com.exavalu.budgetbakersb.services.UserService;
import com.exavalu.budgetbakersb.utilities.EmailService;
import com.exavalu.budgetbakersb.utilities.GenerateCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgetPasswordController {

	@Autowired
	public UserService userService;

	@Autowired
	public RecordService2 recordService2;

	@Autowired
	public AccountService2 accountService2;

	@Autowired
	public MenuService menuService;

	@Autowired
	RecordServices recordService = new RecordServices();

	@Autowired
	EmailService emailService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	AccountService accountService;

	@GetMapping("/forgetPassword")
	public String forgetPassword() {
		return "ForgetPassword/forgetPassword";
	}

	@PostMapping("/forgetPasswordCreateNew")
	public String forgetPasswordUpdate(@RequestParam String email, Model model) {
		String verificationCode = GenerateCode.generateCode();

		String recipient = email;
		String subject = "Temporary Password for Budget Tracker";
		String template = "Hello, user, \n\n" + "We hope you're having a great day! " + "This is your new password. "
				+ "Please change it before login.\n\n" + verificationCode + "\n\n Best regards,\n"
				+ "Exavalu Budget Tracker";
		boolean success = false;
		success = emailService.sendEmail(recipient, subject, template);
		success = userService.updateVerificationCode(verificationCode, email);
		String password = encoder.encode(verificationCode);

		System.out.println(success);

		if (success) {
			success = userService.updatePassword(email, password);
			// Call the service

			if (success) {
				model.addAttribute("FORGOTPASSWORDEMAIL", email);
				return "ForgetPassword/forgetPasswordCreateNew";
			} else {
				return "error";
			}
		}
		return "login";
	}

	@PostMapping("/forgotPasswordUpdate")
	public String forgotPasswordUpdate(@RequestParam String email, @RequestParam String password,
			@RequestParam String oldpassword, Model model) {
		boolean success = true;

		String verificationCode = userService.getVerificationCode(email);
		System.out.println(verificationCode + " " + password + " " + success);

		if (verificationCode.equals(password))
			success = false;
		System.out.println(verificationCode + " " + oldpassword + " " +success);

		password = encoder.encode(password);
		System.out.println(verificationCode + " " + oldpassword + " " +success);
		if (!success || !verificationCode.equals(oldpassword))
			success = false;
		System.out.println(success);

		// Save user with code in DB, then forward the request to verify page
		if (success) {
			success = userService.updatePassword(email, password);
			System.out.println(success);

			// Call the service

			if (success) {
				return "ForgetPassword/forgetPasswordUpdated";
			} else {
				return "error";
			}

		}
		model.addAttribute("FORGOTPASSWORDEMAIL", email);

		return "ForgetPassword/forgetPasswordCreateNew";
	}

}
