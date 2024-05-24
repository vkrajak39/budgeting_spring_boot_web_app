package com.exavalu.budgetbakersb.pojos;

import java.util.List;

public class FilterData {

	
	private String action;
    private List<Integer> accountCheckedList;
    private List<Integer> categoryCheckedList;
    private List<Integer> currencyCheckedList;
    private List<Integer> paymentTypeCheckedList;
    private List<Integer> paymentStatusCheckedList;
    private List<Integer> labelCheckedList;
    private List<Integer> recordTypeCheckedList;
    private List<Integer> monthCheckedList;
    private String startingDate;
    private String endingDate;
    private String searchString;
    
    
    
    
    
    
    
	public FilterData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FilterData(String action, List<Integer> accountCheckedList, List<Integer> categoryCheckedList,
			List<Integer> currencyCheckedList, List<Integer> paymentTypeCheckedList,
			List<Integer> paymentStatusCheckedList, List<Integer> labelCheckedList, List<Integer> recordTypeCheckedList,
			List<Integer> monthCheckedList, String startingDate, String endingDate, String searchString) {
		super();
		this.action = action;
		this.accountCheckedList = accountCheckedList;
		this.categoryCheckedList = categoryCheckedList;
		this.currencyCheckedList = currencyCheckedList;
		this.paymentTypeCheckedList = paymentTypeCheckedList;
		this.paymentStatusCheckedList = paymentStatusCheckedList;
		this.labelCheckedList = labelCheckedList;
		this.recordTypeCheckedList = recordTypeCheckedList;
		this.monthCheckedList = monthCheckedList;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		this.searchString = searchString;
	}
	@Override
	public String toString() {
		return "FilterData [action=" + action + ", accountCheckedList=" + accountCheckedList + ", categoryCheckedList="
				+ categoryCheckedList + ", currencyCheckedList=" + currencyCheckedList + ", paymentTypeCheckedList="
				+ paymentTypeCheckedList + ", paymentStatusCheckedList=" + paymentStatusCheckedList
				+ ", labelCheckedList=" + labelCheckedList + ", recordTypeCheckedList=" + recordTypeCheckedList
				+ ", monthCheckedList=" + monthCheckedList + ", startingDate=" + startingDate + ", endingDate="
				+ endingDate + ", searchString=" + searchString + "]";
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<Integer> getAccountCheckedList() {
		return accountCheckedList;
	}
	public void setAccountCheckedList(List<Integer> accountCheckedList) {
		this.accountCheckedList = accountCheckedList;
	}
	public List<Integer> getCategoryCheckedList() {
		return categoryCheckedList;
	}
	public void setCategoryCheckedList(List<Integer> categoryCheckedList) {
		this.categoryCheckedList = categoryCheckedList;
	}
	public List<Integer> getCurrencyCheckedList() {
		return currencyCheckedList;
	}
	public void setCurrencyCheckedList(List<Integer> currencyCheckedList) {
		this.currencyCheckedList = currencyCheckedList;
	}
	public List<Integer> getPaymentTypeCheckedList() {
		return paymentTypeCheckedList;
	}
	public void setPaymentTypeCheckedList(List<Integer> paymentTypeCheckedList) {
		this.paymentTypeCheckedList = paymentTypeCheckedList;
	}
	public List<Integer> getPaymentStatusCheckedList() {
		return paymentStatusCheckedList;
	}
	public void setPaymentStatusCheckedList(List<Integer> paymentStatusCheckedList) {
		this.paymentStatusCheckedList = paymentStatusCheckedList;
	}
	public List<Integer> getLabelCheckedList() {
		return labelCheckedList;
	}
	public void setLabelCheckedList(List<Integer> labelCheckedList) {
		this.labelCheckedList = labelCheckedList;
	}
	public List<Integer> getRecordTypeCheckedList() {
		return recordTypeCheckedList;
	}
	public void setRecordTypeCheckedList(List<Integer> recordTypeCheckedList) {
		this.recordTypeCheckedList = recordTypeCheckedList;
	}
	public List<Integer> getMonthCheckedList() {
		return monthCheckedList;
	}
	public void setMonthCheckedList(List<Integer> monthCheckedList) {
		this.monthCheckedList = monthCheckedList;
	}
	public String getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}
	public String getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    

    
    
  
    
    
}
