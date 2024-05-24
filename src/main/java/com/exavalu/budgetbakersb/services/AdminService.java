package com.exavalu.budgetbakersb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Label;
import com.exavalu.budgetbakersb.entity.PaymentType;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.repository.CategoryRepository;
import com.exavalu.budgetbakersb.repository.LabelRepository;
import com.exavalu.budgetbakersb.repository.PaymentTypeRepository;

@Service
public class AdminService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private PaymentTypeRepository paymentTypeRepository;
	
	public List<Category> getCategoryForReadCategory() {

		List<Object[]> result = categoryRepository.findCategoryForReadCategory();
		List<Category> categoryList = new ArrayList<>();

		for (Object[] row : result) {
			Category category = new Category();
			category.setCategoryId((Integer) row[0]);
			category.setCategoryName((String) row[1]);
			category.setRecordTypeId((Integer) row[2]);
			category.setRecordTypeName((String) row[3]);
			categoryList.add(category);
		}

		return categoryList;
	}

	public boolean addCategoryForCreateCategory(String categoryName, String recordType) {

		Integer recordTypeId = Integer.parseInt(recordType);
		int result = categoryRepository.addCategoryForReadCategory(categoryName, recordTypeId);
		if (result > 0) {
			return true;
		}
		return false;

	}

	public boolean updateCategoryforEditCategory(int categoryId, String categoryName, int recordType) {

		int result = categoryRepository.updateCategoryForEditCategory(categoryId, categoryName, recordType);
		if (result > 0) {
			return true;
		}
		return false;
	}

	public List<Label> getLabelForReadLabel() {
		
		List<Object[]> result = labelRepository.findLabelForReadLabel();
		List<Label> labelList = new ArrayList<>();
		

		for (Object[] row : result) {
			Label label = new Label();
			label.setLabelId((Integer) row[0]);
			label.setLabelName((String) row[1]);
			label.setCategoryName((String) row[2]);
			label.setCategoryId((Integer) row[3]);
			labelList.add(label);
		}

		return labelList;
	}

	public boolean addLabelForCreateLabel(String labelName, int category) {
		
		int result = labelRepository.addLabelForReadLabel(labelName, category);
		if (result > 0) {
			return true;
		}
		return false;
	}

	public boolean updateLabelForEditLabel(int labelId, String labelName, int category) {
		int result = labelRepository.updateLabelForEditLabel(labelId, labelName, category);
		if (result > 0) {
			return true;
		}
		return false;
	}

	public List<PaymentType> getPaymentTypeForReadPaymentType() {
		
		List<Object[]> result = paymentTypeRepository.findPaymentTypeForReadPaymentType();
		List<PaymentType> paymentTypeList = new ArrayList<>();

		for (Object[] row : result) {
			PaymentType paymentType = new PaymentType();
			paymentType.setId((Integer) row[0]);
			paymentType.setName((String) row[1]);
			paymentTypeList.add(paymentType);
		}

		return paymentTypeList;
	}

	public boolean updatePaymentTypeForEditPaymentType(int paymentTypeId, String paymentTypeName) {
		int result = paymentTypeRepository.updatePaymentTypeForPaymentType(paymentTypeId, paymentTypeName);
		if (result > 0) {
			return true;
		}
		return false;
	}

	public boolean addPaymentTypeForCreatePaymentType(String paymentTypeName) {
		int result = paymentTypeRepository.addPaymentTypeForCreatePaymentType(paymentTypeName);
		if (result > 0) {
			return true;
		}
		return false;
	}
	

}
