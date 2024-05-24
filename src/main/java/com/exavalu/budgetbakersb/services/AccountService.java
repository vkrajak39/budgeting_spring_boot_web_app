package com.exavalu.budgetbakersb.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.repository.AccountRepository;
import com.exavalu.budgetbakersb.repository.AccountTypeRepository;
import com.exavalu.budgetbakersb.repository.CurrencyRepository;
import com.exavalu.budgetbakersb.config.CustomUserDetailsService;

@Service
public class AccountService extends CustomUserDetailsService{
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	@Autowired
	private CurrencyRepository currencyRepository;
	
	public List<Account> getAllAccounts(int userId) {
		return accountRepository.findByUserId(userId);
	}


	public List<AccountType> getAllAccountType() {
		return accountTypeRepository.findAll();
	}
	
	public List<Currency> getAllCurrency() {
		return currencyRepository.findAll();
	}


	public Account save(Account account) {
		return accountRepository.save(account);
	}


	public List<Account> getAccountByName(String name) {
		return accountRepository.findByName(name);
	}


	public List<Account> getAccountById(int id) {
		return accountRepository.findById(id);
	}


	public int getTotalNumberOfAccounts(int userid) {
		int totalAccounts = accountRepository.getTotalNumberOfAccounts(userid);
		// TODO Auto-generated method stub
		return totalAccounts;
	}


//	public User getCurrentUser() {
//        return emp;
//	}
	
	

}
