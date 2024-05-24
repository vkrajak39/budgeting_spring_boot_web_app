package com.exavalu.budgetbakersb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Label;
import com.exavalu.budgetbakersb.entity.PaymentType;
import com.exavalu.budgetbakersb.entity.Report;
import com.exavalu.budgetbakersb.services.AdminService;
import com.exavalu.budgetbakersb.services.UserService2;

@Controller
public class AdminController {

	@Autowired
	UserService2 userService2 = new UserService2();

	@Autowired
	AdminService adminService = new AdminService();

	@GetMapping("/Category")
	public String getCategory(Model model, Authentication authentication, @RequestParam String categoryCRUD) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("Category" + categoryCRUD);
		return "redirect:/" + categoryCRUD;
	}

	@GetMapping("/CreateCategory")
	public String getCreateCategory(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("CreateCategory");
		return "createCategory";
	}

	@GetMapping("/ReadCategory")
	public String getReadCategory(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("ReadCategory");
		List<Category> categoryList = adminService.getCategoryForReadCategory();
		model.addAttribute("CATEGORYLIST", categoryList);
		return "readCategory";
	}

	@GetMapping("/EditCategory")
	public String getEditCategory(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("EditCategory");
		List<Category> categoryList = adminService.getCategoryForReadCategory();
		model.addAttribute("CATEGORYLIST", categoryList);
		return "editCategory";
	}

	@PostMapping("/AddCategory")
	public String getAddCategory(Model model, Authentication authentication, @RequestParam String categoryName,
			@RequestParam String recordType) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("AddCategory");
		boolean result = adminService.addCategoryForCreateCategory(categoryName, recordType);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}

	@PostMapping("/UpdateCategory")
	public String getUpdateCategory(Model model, Authentication authentication, @RequestParam String categoryName,
			@RequestParam int categoryId, @RequestParam int recordType) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("AddCategory");
		boolean result = adminService.updateCategoryforEditCategory(categoryId, categoryName, recordType);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}
	
	@GetMapping("/Label")
	public String getLabel(Model model, Authentication authentication, @RequestParam String labelCRUD) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		return "redirect:/" + labelCRUD;
	}

	@GetMapping("/CreateLabel")
	public String getCreateLabel(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Category> categoryList = adminService.getCategoryForReadCategory();
		model.addAttribute("CATEGORYLIST", categoryList);
		return "createLabel";
	}

	@GetMapping("/ReadLabel")
	public String getReadLabel(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Label> labelList = adminService.getLabelForReadLabel();
		model.addAttribute("LABELLIST", labelList);
		return "readLabel";
	}

	@GetMapping("/EditLabel")
	public String getEditLabel(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Label> labelList = adminService.getLabelForReadLabel();
		model.addAttribute("LABELLIST", labelList);
		List<Category> categoryList = adminService.getCategoryForReadCategory();
		model.addAttribute("CATEGORYLIST", categoryList);
		return "editLabel";
	}

	@PostMapping("/AddLabel")
	public String getAddLabel(Model model, Authentication authentication, @RequestParam String labelName,
			@RequestParam int category) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		boolean result = adminService.addLabelForCreateLabel(labelName, category);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}

	@PostMapping("/UpdateLabel")
	public String getUpdateLabel(Model model, Authentication authentication, @RequestParam String labelName,
			@RequestParam int labelId, @RequestParam int category) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		boolean result = adminService.updateLabelForEditLabel(labelId, labelName, category);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}
	
	@GetMapping("/PaymentType")
	public String getPaymentType(Model model, Authentication authentication, @RequestParam String paymentTypeCRUD) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		return "redirect:/" + paymentTypeCRUD;
	}

	@GetMapping("/CreatePaymentType")
	public String getCreatePaymentType(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		return "createPaymentType";
	}

	@GetMapping("/ReadPaymentType")
	public String getReadPaymentType(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<PaymentType> paymentList = adminService.getPaymentTypeForReadPaymentType();
		model.addAttribute("PAYMENTTYPELIST", paymentList);
		return "readPaymentType";
	}

	@GetMapping("/EditPaymentType")
	public String getEditPaymentType(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<PaymentType> paymentList = adminService.getPaymentTypeForReadPaymentType();
		model.addAttribute("PAYMENTTYPELIST", paymentList);
		return "editPaymentType";
	}

	@PostMapping("/AddPaymentType")
	public String getAddPaymentType(Model model, Authentication authentication, @RequestParam String paymentTypeName) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		boolean result = adminService.addPaymentTypeForCreatePaymentType(paymentTypeName);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}

	@PostMapping("/UpdatePaymentType")
	public String getUpdatePaymentType(Model model, Authentication authentication, @RequestParam String paymentTypeName,
			@RequestParam int paymentTypeId) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		boolean result = adminService.updatePaymentTypeForEditPaymentType(paymentTypeId, paymentTypeName);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "Dashboard/dashboard";
	}

}
