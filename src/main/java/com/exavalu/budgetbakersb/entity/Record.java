package com.exavalu.budgetbakersb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double amount;
	private String date;
	private String time;
	private String payer;
	private String note;
	private String place;
	@Column(nullable = true)
	private double toAmount;
	
	@ManyToOne
	@JoinColumn(name="userId",insertable=false,updatable=false)
	private User user;
	private int userId;
	
	@ManyToOne
	@JoinColumn(name="recordTypeId",insertable=false,updatable=false)
	private RecordType recordType;
	private int recordTypeId;
	
	@ManyToOne
	@JoinColumn(name="currencyId",insertable=false,updatable=false)
	private Currency currency;
	private int currencyId;
	
	@ManyToOne
	@JoinColumn(name="accountId",insertable=false,updatable=false)
	private Account account;
	private int accountId;
	
	@ManyToOne
	@JoinColumn(name="categoryId",insertable=false,updatable=false)
	private Category category;
	private int categoryId;
	
	@ManyToOne
	@JoinColumn(name="labelId" ,insertable=false,updatable=false)
	private Label label;
	private int labelId;
	
	@ManyToOne
	@JoinColumn(name="paymentTypeId" ,insertable=false,updatable=false)
	private PaymentType paymentType;
	private int paymentTypeId;
	
	@ManyToOne
	@JoinColumn(name="paymentStatusId" ,insertable=false,updatable=false)
	private PaymentStatus paymentStatus;
	private int paymentStatusId;
	
	
//	@ManyToOne
//	@JoinColumn(name="toAccountId",insertable=false,updatable=false)
//	private Account toAccount;
	@Column(nullable = true)
	private int toAccountId;
	
//	@ManyToOne
//	@JoinColumn(name="toCurrencyId",insertable=false,updatable=false)
//	private Currency toCurrency;
	@Column(nullable = true)
	private int toCurrencyId;
	
	
	
	@Override
	public String toString() {
		return "Record [id=" + id + ", amount=" + amount + ", date=" + date + ", time=" + time + ", payer=" + payer
				+ ", note=" + note + ", place=" + place + ", toAmount=" + toAmount + ", user=" + user + ", userId="
				+ userId + ", recordType=" + recordType + ", recordTypeId=" + recordTypeId + ", currency=" + currency
				+ ", currencyId=" + currencyId + ", account=" + account + ", accountId=" + accountId + ", category="
				+ category + ", categoryId=" + categoryId + ", label=" + label + ", labelId=" + labelId
				+ ", paymentType=" + paymentType + ", paymentTypeId=" + paymentTypeId + ", paymentStatus="
				+ paymentStatus + ", paymentStatusId=" + paymentStatusId + ", toAccountId=" + toAccountId
				+ ", toCurrencyId=" + toCurrencyId + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	public double getToAmount() {
		return toAmount;
	}
	public void setToAmount(double toAmount) {
		this.toAmount = toAmount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public RecordType getRecordType() {
		return recordType;
	}
	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}
	public int getRecordTypeId() {
		return recordTypeId;
	}
	public void setRecordTypeId(int recordTypeId) {
		this.recordTypeId = recordTypeId;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		paymentStatus = paymentStatus;
	}
	public int getPaymentStatusId() {
		return paymentStatusId;
	}
	public void setPaymentStatusId(int paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}
//	public Account getToAccount() {
//		return toAccount;
//	}
//	public void setToAccount(Account toAccount) {
//		this.toAccount = toAccount;
//	}
	public int getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}
//	public Currency getToCurrency() {
//		return toCurrency;
//	}
//	public void setToCurrency(Currency toCurrency) {
//		this.toCurrency = toCurrency;
//	}
	public int getToCurrencyId() {
		return toCurrencyId;
	}
	public void setToCurrencyId(int toCurrencyId) {
		this.toCurrencyId = toCurrencyId;
	}
	
}
