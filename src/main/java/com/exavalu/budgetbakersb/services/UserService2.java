package com.exavalu.budgetbakersb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Report;
import com.exavalu.budgetbakersb.repository.CategoryRepository;
import com.exavalu.budgetbakersb.repository.ReportRepository;
import com.exavalu.budgetbakersb.repository.UserRepository;

@Service
public class UserService2 {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Report> getAllReport() {
		return reportRepository.findAll();
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	

}
