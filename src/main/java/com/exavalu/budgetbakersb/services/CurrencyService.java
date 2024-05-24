package com.exavalu.budgetbakersb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.repository.CurrencyRepository;

@Service
public class CurrencyService {

	
	@Autowired
	CurrencyRepository currencyRepository;

	public Currency getCurrencyById(int toCurrencyId) {
		
		
		
		return currencyRepository.getReferenceById(toCurrencyId);
	}
	
	
	
	
}
