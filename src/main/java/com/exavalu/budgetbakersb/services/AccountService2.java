package com.exavalu.budgetbakersb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.repository.AccountRepository;
import com.exavalu.budgetbakersb.repository.RecordRepository;

@Service
public class AccountService2 {
	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private AccountRepository accountRepository;

	public double getAccountExpense(User user, String givenDate1, String givenDate2, int id) {
		double expense = recordRepository.sumAmountByUserIdAndRecordTypeAndDateBetweenAndAccountId(user.getUserid(), 1,
				givenDate1, givenDate2, id);
		return -1.0 * expense;
	}

	public double getAccountIncome(User user, String givenDate1, String givenDate2, int id) {

		double income = recordRepository.sumAmountByUserIdAndRecordTypeAndDateBetweenAndAccountId(user.getUserid(), 2,
				givenDate1, givenDate2, id);
		return income;
	}

	public double getAccountNegativeTransfer(User user, String givenDate1, String givenDate2, int id) {
		double transfer = recordRepository.sumNegativeTransferAmount(user.getUserid(), givenDate1, givenDate2, id);
		return -1.0 * transfer;
	}

	public double getAccountInitialAmount(User user, int id) {
		return recordRepository.sumAccountInitialAmount(user.getUserid(),4, id);
	}

	public double getAccountIncomeTransfer(User user, String givenDate1, String givenDate2, int id) {
		double transfer = recordRepository.sumIncomeTransferAmount(user.getUserid(), givenDate1, givenDate2, id);
		return -1.0 * transfer;
	}

	public HashMap<String, Double> getAccountExpenseCategoryPercentage(User user, String givenDate1, String givenDate2,int id) {
		HashMap<String, Double> expenseCategory = new HashMap<>();

		// Call the repository method to fetch the data
		List<Object[]> result = recordRepository.getAccountExpenseCategoryPercentage(user.getUserid(), givenDate1, givenDate2,id);

		// Extract the result into the map
		for (Object[] row : result) {
			String categoryName = (String) row[0];
			Double percentageExpense = (Double) row[1];
			expenseCategory.put(categoryName, percentageExpense);
		}

		return expenseCategory;
	}
	
	public List<Account> getAllAccounts(int userId) {
		return accountRepository.findByUserId(userId);
	}
}
