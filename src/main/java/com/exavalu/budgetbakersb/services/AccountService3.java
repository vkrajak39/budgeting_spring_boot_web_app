package com.exavalu.budgetbakersb.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.repository.AccountRepository;

@Service
public class AccountService3 {

	@Autowired
	private AccountRepository accountRepository;

	public double getTotalAmount(int userid, String accountId) {
		List<Integer> accountIdList = Arrays.stream(accountId.split(","))
                .map(String::trim) // Trim each token to remove spaces
                .map(Integer::parseInt)
                .collect(Collectors.toList());
		System.out.println("Inside getTotalAmount" + accountId);
		double result = accountRepository.findTotalAmountByUserIdAndAccountId(userid, accountIdList);
		System.out.println("the result value" + result);
		return result;
	}

}
