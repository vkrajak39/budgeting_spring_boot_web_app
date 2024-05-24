package com.exavalu.budgetbakersb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.entity.Label;
import com.exavalu.budgetbakersb.entity.PaymentStatus;
import com.exavalu.budgetbakersb.entity.PaymentType;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.entity.RecordType;
import com.exavalu.budgetbakersb.pojos.FilterData;
import com.exavalu.budgetbakersb.repository.CurrencyRepository;
import com.exavalu.budgetbakersb.services.AccountService;
import com.exavalu.budgetbakersb.services.CurrencyService;
import com.exavalu.budgetbakersb.services.RecordServices;
import org.springframework.security.core.Authentication;



@Controller
public class RecordController {
	
	@Autowired
	RecordServices recordServices;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	CurrencyService currencyService;
	
//	@GetMapping("/showRecordModal")
//	public String showRecordModal(@RequestParam String param) {
//		return new String("recordModal");
//	}
	
//	@GetMapping("/testheader")
//	public String testheader(@RequestParam String param) {
//		return new String("testHeader");
//	}
	
//	@GetMapping("/Records")
//	public String redirectToGetRecordPage()
//	{
//		return "redirect:/Records";
//	}
	
	@PostMapping("/records/addRecord")
	public String addRecord(
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "label") int labelId,
			@RequestParam(name = "datepicker") String date,
			@RequestParam(name = "time_Edit") String time,
			@RequestParam(name = "payer") String payer,
			@RequestParam(name = "note") String note,
			@RequestParam(name = "payment_type") int paymentTypeId,
			@RequestParam(name = "payment_status") int paymentStatusId,
			@RequestParam(name = "place") String place,
			
			@RequestParam(name = "Account_type_expense" ) int Account_type_expense,
			@RequestParam(name = "amount_expense", defaultValue = "0",required = false) double amount_expense,
			@RequestParam(name = "currency_expense") int currency_expense,
			
			@RequestParam(name = "Account_type_income") int Account_type_income,
			@RequestParam(name = "amount_income", defaultValue = "0",required = false ) double amount_income,
			@RequestParam(name = "currency_income") int currency_income,
			
			
			@RequestParam(name = "account_type_from_account_transfer") int account_type_from_account_transfer,
			@RequestParam(name = "amount_from_account_transfer", defaultValue = "0",required = false) double amount_from_account_transfer,
			@RequestParam(name = "currency_from_account_transfer") int currency_from_account_transfer,
			@RequestParam(name = "account_type_to_account_transfer") int account_type_to_account_transfer,
			@RequestParam(name = "amount_to_account_transfer", defaultValue = "0",required = false) double amount_to_account_transfer,
			@RequestParam(name = "currency_to_account_transfer") int currency_to_account_transfer,
			
			 Model model,Authentication authentication) {
		
		System.out.println("inside add record controller");
		
		CustomUser user = (CustomUser) authentication.getPrincipal();
		
		
		int userId = user.getUserid();
		
		
		
		int recordTypeId=0;
		double amount=0;
		int currencyId=0;
		int accountId=0;
		
		int toAccountId=0;
		double toAmount=0;
		int toCurrencyId=0;
		
		
		System.out.println("\n\n");
		

		System.out.println("userId "+userId);
		
		System.out.println("currency_expense "+ currency_expense);
		
		System.out.println("amount_expense " + amount_expense);
		
		System.out.println("Account_type_expense "+Account_type_expense);
		
		System.out.println("place "+place);
		
		System.out.println("paymentStatusId " + paymentStatusId);
		
		System.out.println("paymentTypeId " +paymentTypeId);
		
		System.out.println("note "+note);
		
		System.out.println("payer "+payer);
		
//		System.out.println("time " + time);

//		System.out.println("date " +date);
		
		System.out.println("labelId "+ labelId);
		
		System.out.println("categoryId " + categoryId);
		
		
		
		System.out.println(" Account_type_income "+ Account_type_income +" end");
		
		System.out.println("amount_income "+ Account_type_income + " end of amount_income");
		
//		
//		System.out.println("amount_from_account_transfer " + amount_from_account_transfer);
//		
//		System.out.println("amount_to_account_transfer "+ amount_to_account_transfer);


		System.out.println("account_type_from_account_transfer "+ account_type_from_account_transfer);
		
		System.out.println("amount_from_account_transfer "+amount_from_account_transfer);
		
		System.out.println("currency_from_account_transfer "+currency_from_account_transfer);
		
		System.out.println("amount_to_account_transfer "+amount_to_account_transfer);
		
		System.out.println("currency_to_account_transfer "+currency_to_account_transfer);
		
		System.out.println("account_type_to_account_transfer "+account_type_to_account_transfer);
		
		
//		for expense
		if(Account_type_expense>0 && Account_type_income<=0 && account_type_from_account_transfer<=0 )
		{
			recordTypeId=1;
			amount=amount_expense;
			currencyId= currency_expense;
			accountId =Account_type_expense;
			
			

		}
		
//		for income	
		if(Account_type_expense<=0 && Account_type_income>0 && account_type_from_account_transfer<=0 )
		{
			recordTypeId=2;
			amount=amount_income;
			currencyId= currency_income;
			accountId =Account_type_income;
			
		}
		
		
		
//		for transfer
		if(Account_type_expense<=0 && Account_type_income<=0 && account_type_from_account_transfer>0 )
		{
			recordTypeId=3;
			amount=amount_from_account_transfer;
			currencyId= currency_from_account_transfer;
			accountId =account_type_from_account_transfer;
			
			toAmount = amount_to_account_transfer;
			toCurrencyId = currency_to_account_transfer;
			toAccountId = account_type_to_account_transfer;
			
			
		}
		
		
		
		
//		saving record values
		
		Record record = new Record();
		record.setUserId(userId);
		record.setRecordTypeId(recordTypeId);
		
		record.setCategoryId(categoryId);
		record.setLabelId(labelId);
		record.setDate(date);
		record.setTime(time);		
		record.setPayer(payer);
		record.setPaymentTypeId(paymentTypeId);
		record.setPaymentStatusId(paymentStatusId);
		record.setNote(note);
		record.setPlace(place);
		record.setAccountId(accountId);
		record.setCurrencyId(currencyId);
		
		if(recordTypeId==1 || recordTypeId ==3)
		{
			record.setAmount(amount*(-1));
		}
		else {
			record.setAmount(amount);
		}
		
		
		record.setToAccountId(toAccountId);
		record.setToCurrencyId(toCurrencyId);
		record.setToAmount(toAmount);
		
		
		
		
		System.out.println("\n\n");
		
		System.out.println(record.getAmount());
		System.out.println(record.getToAmount());
		System.out.println(record.getCurrencyId());
		System.out.println(record.getToCurrencyId());
		System.out.println(record.getAccountId());
		System.out.println(record.getToAccountId());
		
		
		
		System.out.println("recordType id is "+ record.getRecordTypeId());
		
		recordServices.saveRecord(record);
		
		
		return "redirect:/Records";
		
		
	}
	
	
	
	@GetMapping("/records/CategoryList")
	public String getCategoryList(Model model)
	{
		
		List<Category> categoryList = recordServices.getAllCategories();
	
		model.addAttribute("categoryList",categoryList);
		
		return "category";

	}
	
	@GetMapping("/records/LabelList")
	public String getLabelList(Model model)
	{
		
		List<Label> labelList = recordServices.getAllLabels();
		
		
		model.addAttribute("labelList",labelList);
		
		
		return "label";

	}
	
	
	
	@GetMapping("/records/PaymentTypeList")
	public String getPaymentTypeList(Model model)
	{
		
		List<PaymentType> paymentTypeList = recordServices.getAllPaymentTypes();
		
		
	
		model.addAttribute("paymentTypeList",paymentTypeList);
		
		return "paymentType";

	}

	
	
	@GetMapping("/records/PaymentStatusList")
	public String getPaymentStatusList(Model model)
	{
		
		List<PaymentStatus> paymentStatusList = recordServices.getAllPaymentStatus();
	
		model.addAttribute("paymentStatusList",paymentStatusList);
		
		return "paymentStatus";

	}
	
	
	
	@GetMapping("/records/CurrencyList")
	public String getCurrencyList(Model model)
	{
		
		List<Currency> currencyList = recordServices.getAllCurrencies();
	
		model.addAttribute("currencyList",currencyList);
		
		return "currency";

	}
	
	@GetMapping("/records/AccountTypeList")
	public String getAccountTypeList(Model model)
	{
		
		List<AccountType> accountTypeList = recordServices.getAllAccountType();
		
		System.out.println("account type list size is  " + accountTypeList.size() );
		
		model.addAttribute("accountTypeList",accountTypeList);
		
		return "accountType";

	}
	
	@GetMapping("/records/AccountList")
	public String getAccountList(Model model,Authentication authentication)
	{
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		
		List<Account> accountList = recordServices.getAllAccountOfUser(user.getUserid());
		
		//System.out.println("account type list size is  " + accountList.size() );
		
		model.addAttribute("accountList",accountList);
		
		return "accountOfUser";

	}
	
	
	
	
	
	
	@GetMapping("/Records")
	public String getRecordTable(Authentication authentication,Model model)
	{
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		int userId = user.getUserid();
		
		
		System.out.println("inside records table \n\n");
		
		List<Account> accountList = recordServices.getAllAccountOfUser(user.getUserid());
		List<Currency> currencyList = recordServices.getAllCurrencies();
		List<PaymentStatus> paymentStatusList = recordServices.getAllPaymentStatus();
		List<PaymentType> paymentTypeList = recordServices.getAllPaymentTypes();
		List<Label> labelList = recordServices.getAllLabels();
		List<Category> categoryList = recordServices.getAllCategories();
		
		List<Record> recordListByUserId = recordServices.getAllRecordsByUserId(userId);
		List<RecordType> recordTypeList = recordServices.getAllRecordTypeList();
		
		
		model.addAttribute("users", user);
		
		model.addAttribute("recordListByUserId", recordListByUserId);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("labelList",labelList);
		model.addAttribute("paymentTypeList",paymentTypeList);		
		model.addAttribute("paymentStatusList",paymentStatusList);		
		model.addAttribute("currencyList",currencyList);
		model.addAttribute("accountList",accountList);
		model.addAttribute("recordTypeList",recordTypeList);
		
		
		
		System.out.println("account list size "+ accountList.size());
		return "records";
//		return "dummy";
	}
	
	
	
	@GetMapping("/records/editRecord")
	public String editRecord( @RequestParam(name = "recordId", required = false , defaultValue = "10")int recordId , Model model,Authentication authentication)
	{
		CustomUser user = (CustomUser) authentication.getPrincipal();
		
		System.out.println("inside edit record page " +recordId);
		System.out.println("recordId is "+recordId);
		
		Record record = recordServices.getRecordByRecordId(recordId);
		int recordTypeId = record.getRecordTypeId();
		
		List<Account> accountList = recordServices.getAllAccountOfUser(user.getUserid());
		List<Currency> currencyList = recordServices.getAllCurrencies();
		List<PaymentStatus> paymentStatusList = recordServices.getAllPaymentStatus();
		List<PaymentType> paymentTypeList = recordServices.getAllPaymentTypes();
		List<Label> labelList = recordServices.getAllLabels();
		List<Category> categoryList = recordServices.getAllCategories();
		
		
		List<Account> toAccount = null;
		
		
		Currency toCurrency = null ;
		
		
		
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("labelList",labelList);
		model.addAttribute("paymentTypeList",paymentTypeList);		
		model.addAttribute("paymentStatusList",paymentStatusList);		
		model.addAttribute("currencyList",currencyList);
		model.addAttribute("accountList",accountList);
		model.addAttribute("record", record);
		model.addAttribute("user", user);
		
		model.addAttribute("recordId",recordId);
		model.addAttribute("recordTypeId",recordTypeId);
		
		
		if(record.getToAccountId() != 0)
		{
			toAccount = (List<Account> ) accountService.getAccountById(record.getToAccountId());
			model.addAttribute("toAccount",toAccount.get(0));
		}else {
			
			
			
			Account noToAccount = new Account();
			noToAccount.setCurrencyId(0);
			noToAccount.setName("no such account");
			model.addAttribute("toAccount",noToAccount);
			
		}
		
		
		if(record.getToCurrencyId() != 0)
		{
			toCurrency = currencyService.getCurrencyById(record.getToCurrencyId());
			model.addAttribute("toCurrency", toCurrency);
		}
		else
		{
			Currency noToCurrency = new Currency();
			noToCurrency.setId(0);
			noToCurrency.setCurrency_name("no such currency");
			model.addAttribute("toCurrency", noToCurrency);
		}
		
		
		return "editRecordPage";
//		return "testHeader";
	}
	
	
	
	
	@PostMapping("/records/updateRecord")
	public String updateRecordData(Model model,Authentication authentication,
			@RequestParam(name="recordId" )int recordId, 
			
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "label") int labelId,
			@RequestParam(name = "datepicker") String date,
			@RequestParam(name = "time_Edit") String time,
			@RequestParam(name = "payer") String payer,
			@RequestParam(name = "note") String note,
			@RequestParam(name = "payment_type") int paymentTypeId,
			@RequestParam(name = "payment_status") int paymentStatusId,
			@RequestParam(name = "place") String place,
			
			@RequestParam(name = "Account_type_expense" ) int Account_type_expense,
			@RequestParam(name = "amount_expense", defaultValue = "0",required = false) double amount_expense,
			@RequestParam(name = "currency_expense") int currency_expense,
			
			@RequestParam(name = "Account_type_income") int Account_type_income,
			@RequestParam(name = "amount_income", defaultValue = "0",required = false ) double amount_income,
			@RequestParam(name = "currency_income") int currency_income,
			
			
			@RequestParam(name = "account_type_from_account_transfer") int account_type_from_account_transfer,
			@RequestParam(name = "amount_from_account_transfer", defaultValue = "0",required = false) double amount_from_account_transfer,
			@RequestParam(name = "currency_from_account_transfer") int currency_from_account_transfer,
			@RequestParam(name = "account_type_to_account_transfer") int account_type_to_account_transfer,
			@RequestParam(name = "amount_to_account_transfer", defaultValue = "0",required = false) double amount_to_account_transfer,
			@RequestParam(name = "currency_to_account_transfer") int currency_to_account_transfer
			
			)
	{
		
		System.out.println("inside update record "+recordId);
		
		
		CustomUser user = (CustomUser) authentication.getPrincipal();
		int userId = user.getUserid();
		
		
		
//		for deciding the record type of the new data
		
		int recordTypeId=0;
		double amount=0;
		int currencyId=0;
		int accountId=0;
		
		int toAccountId=0;
		double toAmount=0;
		int toCurrencyId=0;
		
		
		
//		for expense
		if(Account_type_expense>0 && Account_type_income<=0 && account_type_from_account_transfer<=0 )
		{
			recordTypeId=1;
			amount=amount_expense;
			currencyId= currency_expense;
			accountId =Account_type_expense;
			
			

		}
		
//		for income	
		if(Account_type_expense<=0 && Account_type_income>0 && account_type_from_account_transfer<=0 )
		{
			recordTypeId=2;
			amount=amount_income;
			currencyId= currency_income;
			accountId =Account_type_income;
			
		}
		
		
		
//		for transfer
		if(Account_type_expense<=0 && Account_type_income<=0 && account_type_from_account_transfer>0 )
		{
			recordTypeId=3;
			amount=amount_from_account_transfer;
			currencyId= currency_from_account_transfer;
			accountId =account_type_from_account_transfer;
			
			toAmount = amount_to_account_transfer;
			toCurrencyId = currency_to_account_transfer;
			toAccountId = account_type_to_account_transfer;
			
			
		}
		
		

//getting old record to update values		
		Record oldRecord = recordServices.getRecordByRecordId(recordId);
		
		oldRecord.setCategoryId(categoryId);
		oldRecord.setLabelId(labelId);
		oldRecord.setDate(date);
		oldRecord.setTime(time);
		oldRecord.setPayer(payer);
		oldRecord.setPaymentTypeId(paymentTypeId);
		oldRecord.setPaymentStatusId(paymentStatusId);
		oldRecord.setNote(note);
		oldRecord.setPlace(place);
		
		oldRecord.setAccountId(accountId);
		oldRecord.setCurrencyId(currencyId);
		oldRecord.setAmount(amount);
		
		oldRecord.setToAccountId(toAccountId);
		oldRecord.setToCurrencyId(toCurrencyId);
		oldRecord.setToAmount(toAmount);
		
		oldRecord.setRecordTypeId(recordTypeId);
		
		recordServices.saveRecord(oldRecord);
		

		
		List<Record> recordListByUserId = recordServices.getAllRecordsByUserId(userId);
		
		model.addAttribute("recordListByUserId", recordListByUserId);
		
		
		return "redirect:/Records";
		
	}
	
	@PostMapping("/records/deleteRecord")
	public String deleteRecord(@RequestParam(name="recordId" )int recordId, Model model,Authentication authentication)
	{
		
		System.out.println("inside delete record " +recordId);
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		int userId = user.getUserid();
		
		
		
		recordServices.deleteRecordById(recordId);
		List<Record> recordListByUserId = recordServices.getAllRecordsByUserId(userId);
		
		model.addAttribute("recordListByUserId", recordListByUserId);
		
		return "records";
	}
	
	
	
	
	@PostMapping("/records/filterRecordData")
	public String filterRecords(
			@RequestBody FilterData filterData,
			 Model model,
			 Authentication authentication
//			@RequestParam(name="action")String action
//			@RequestParam(name="accountCheckedList")List<Integer> accountCheckedList
//			@RequestParam(name="categoryCheckedList")List categoryCheckedList,
//			@RequestParam(name="currencyCheckedList")List currencyCheckedList
//			@RequestParam(name="paymentTypeCheckedList")List paymentTypeCheckedList,
//			@RequestParam(name="paymentStatusCheckedList")List paymentStatusCheckedList,
//			@RequestParam(name="listCheckedList")List listCheckedList
			)
	
	{
		
		
		System.out.println("inside filter data");
		
		
		
		CustomUser user = (CustomUser) authentication.getPrincipal();
//		model.addAttribute("users", user);
		int userId = user.getUserid();
		
		String action = filterData.getAction();
		List<Integer> accountCheckedList = filterData.getAccountCheckedList();
        List<Integer> categoryCheckedList = filterData.getCategoryCheckedList();
        List<Integer> currencyCheckedList = filterData.getCurrencyCheckedList();
        List<Integer> paymentTypeCheckedList = filterData.getPaymentTypeCheckedList();
        List<Integer> paymentStatusCheckedList = filterData.getPaymentStatusCheckedList();
        List<Integer> labelCheckedList = filterData.getLabelCheckedList();
        List<Integer> recordTypeCheckedList = filterData.getRecordTypeCheckedList();
        List<Integer> monthCheckedList = filterData.getMonthCheckedList();
        String startingDate = filterData.getStartingDate();
        String endingDate = filterData.getEndingDate();
        
        
        
//        String searchString = filterData.getSearchString();
		
//		System.out.println("accountCheckedList "+accountCheckedList);
//		System.out.println("categoryCheckedList "+categoryCheckedList);
//		System.out.println("currencyCheckedList "+currencyCheckedList);
//		System.out.println("paymentTypeCheckedList "+paymentTypeCheckedList);
//		System.out.println("paymentStatusCheckedList "+paymentStatusCheckedList);
//		System.out.println("listCheckedList "+listCheckedList);

        System.out.println("\n\n");
        System.out.println("action "+action);
		System.out.println("accountCheckedList size "+accountCheckedList);
		System.out.println("categoryCheckedList "+categoryCheckedList);
		System.out.println("currencyCheckedList "+currencyCheckedList);
		System.out.println("paymentTypeCheckedList "+paymentTypeCheckedList);
		System.out.println("paymentStatusCheckedList "+paymentStatusCheckedList);
		System.out.println("labelCheckedList "+labelCheckedList);
		System.out.println("recordTypeCheckedList "+ recordTypeCheckedList);
		System.out.println("monthCheckedList "+monthCheckedList);
		System.out.println("startingDate "+startingDate);
		System.out.println("endingDate "+endingDate);
		
		
			System.out.println("move foreward");
//		List<Record> filtered_list = recordServices.getFilteredData(userId,accountCheckedList,
//																		categoryCheckedList,
//																		currencyCheckedList,
//																		paymentTypeCheckedList,
//																		paymentStatusCheckedList,
//																		listCheckedList);
//			
		
		
		System.out.println("\n\n ___@@@@ newwwwwww  ");
//		if(!filtered_list.isEmpty())
//		{
//			filtered_list.forEach(e->{System.out.println(e.toString());});
//		}
		
		
		
		
		
		
		List<Record> testReordList = recordServices.getAllRecordsByUserId(userId);
		
		
		
		
		
//		testing filter by list
		List<Record> testList = recordServices.filterRecordAccordingSidebar(testReordList,accountCheckedList,
				categoryCheckedList,
				currencyCheckedList,
				paymentTypeCheckedList,
				paymentStatusCheckedList,
				labelCheckedList,
				recordTypeCheckedList,
				monthCheckedList,
				startingDate,
				endingDate);
		
		
		if(!testList.isEmpty())
			{
			testList.forEach(e->{System.out.println(e.toString());});
			}
		
		
		
		
		
		model.addAttribute("testReordList",testList);
		
		
		
		return "fliteredRecordTable";
	}
	
	
	
	@GetMapping("/records/testHeader")
	public String getTestHeader(Model model)
	{
		
//		List<String> test = List.of("food","movies","games");
		
		List<Category> categoryList = recordServices.getAllCategories();
		
		
//		if(categoryList!=null)
//		{
//			System.out.println("test header called size of list is  "+ categoryList.size());
//		}
//		else {
//			System.out.println("test header called size of list is  NUll");
//		}
//		
//		model.addAttribute("categoryList",categoryList);
		
		
		model.addAttribute("categoryList",categoryList);
		
		return "testHeader";
	}
	
	
	
	
	@GetMapping("/records/GetTestList")
	public String getTestList(Model model)
	{
		System.out.println("GetTestList controleer called ");
		//List<String> testing = List.of("vineet","ajit","malka");
		List<Category> categoryList = recordServices.getAllCategories();
		model.addAttribute("categoryList",categoryList);
		
		return "testList";

//		return null;
	
	}
	
	@PostMapping("/records/import")
	public String importRecords(@RequestParam("csvFile") MultipartFile file,Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		
		recordServices.processUploadedFile(file,user.getUserid());
		return "testHeader";
	}
	
	@GetMapping("/login-error")
	public String getErrorPage(Model model,
			 Authentication authentication) {
		
		
		 Authentication authentication2 = authentication;
//		model.addAttribute("users", user);
//		int userId = user.getUserid();
		System.out.println("user fronm error page is "+authentication2);
		return "error2";
	}
	

}