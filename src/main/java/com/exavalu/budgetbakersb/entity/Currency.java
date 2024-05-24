package com.exavalu.budgetbakersb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Currency {
	@Id
	private int id;
	private String currency_name;
	@Enumerated(value = EnumType.STRING)
	private CurrencyCodeEnum currencyCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrency_name() {
		return currency_name;
	}
	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}
	public CurrencyCodeEnum getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(CurrencyCodeEnum currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@OneToMany(mappedBy="currency")
	private List<Account>  accounts = new ArrayList<>();

	@OneToMany(mappedBy="currency")
	private List<Record> records = new ArrayList<>();
}
