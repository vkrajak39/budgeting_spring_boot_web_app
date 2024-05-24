package com.exavalu.budgetbakersb.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.repository.AccountRepository;
import com.exavalu.budgetbakersb.repository.RecordRepository;
import com.exavalu.budgetbakersb.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	public double getUserExpense(User user, String givenDate1, String givenDate2) {
		return -1.0 * (recordRepository.sumAmountByUserIdAndRecordTypeAndDateBetween(user.getUserid(), 1, givenDate1,
				givenDate2));
	}

	public double getUserIncome(User user, String givenDate1, String givenDate2) {
		return recordRepository.sumAmountByUserIdAndRecordTypeAndDateBetween(user.getUserid(), 2, givenDate1,
				givenDate2);
	}

	public double getUserNegativeTransfer(User user, String givenDate1, String givenDate2) {
		return -1.0 * recordRepository.sumNegativeTransferAmount(user.getUserid(), givenDate1, givenDate2);
	}

	public double getUserInitialAmount(User user) {

		return recordRepository.sumInitialAmount(user.getUserid(), 4);
	}

	public double getUserIncomeTransfer(User user, String givenDate1, String givenDate2) {
		return -1.0 * recordRepository.sumIncomeTransferAmount(user.getUserid(), givenDate1, givenDate2);
	}

	public HashMap<String, Double> getExpenseCategoryPercentage(User user, String givenDate1, String givenDate2) {
		HashMap<String, Double> expenseCategory = new HashMap<>();
		// Call the repository method to fetch the data
		for (Object[] row : recordRepository.getExpenseCategoryPercentage(user.getUserid(), givenDate1, givenDate2)) {
			String categoryName = (String) row[0];
			Double percentageExpense = (Double) row[1];
			expenseCategory.put(categoryName, percentageExpense);
		}
		return expenseCategory;
	}

	// Forgot Password Code
	public boolean updatePassword(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setPassword(password);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public boolean updateVerificationCode(String verificationCode, String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setVerificationCode(verificationCode);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public String getVerificationCode(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return user.getVerificationCode();
		}
		return null;
	}

}
