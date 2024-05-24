package com.exavalu.budgetbakersb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class AccountType {
	@Id
	private int id;
	private String accountName;
	@Enumerated(value = EnumType.STRING)
    private AccountTypeEnum accountType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public AccountTypeEnum getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountTypeEnum accountType) {
		this.accountType = accountType;
	}

}
