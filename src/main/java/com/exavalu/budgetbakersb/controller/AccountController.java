package com.exavalu.budgetbakersb.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.services.AccountService;
import com.exavalu.budgetbakersb.services.RecordServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.exavalu.budgetbakersb.entity.Record;
import org.springframework.security.core.Authentication;

@Controller
public class AccountController {

	@Autowired
	AccountService accountService = new AccountService();
	@Autowired
	RecordServices recordService = new RecordServices();

	@GetMapping("/Accounts")
	public String getAccounts(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Account> account = accountService.getAllAccounts(user.getUserid());
		model.addAttribute("accounts", account);
		List<AccountType> accountType = accountService.getAllAccountType();
		model.addAttribute("accounttypes", accountType);
		List<Currency> currency = accountService.getAllCurrency();
		model.addAttribute("currency", currency);
		return "account";
	}

	@GetMapping("/accounts/Accounts")
	public String getAccounts() {

		return "redirect:/Accounts";
	}

	@GetMapping("/accounts/Dashboard")
	public String getDashboard() {

		return "redirect:/Dashboard";
	}

	@PostMapping("/accounts/add")
	public String addAccounts(@RequestParam String userId, @RequestParam String accountName,
			@RequestParam String accountType, @RequestParam String amount, @RequestParam String currency)

	{

		Account account = new Account();
		account.setUserId(Integer.parseInt(userId));
		account.setName(accountName.trim());
		account.setAccountTypeId(Integer.parseInt(accountType));
		account.setAmount(Integer.parseInt(amount));
		account.setCurrencyId(Integer.parseInt(currency));
		Account savedAccount = accountService.save(account);
		Record startingRecord = new Record();
		startingRecord.setRecordTypeId(4); // 4 is for starting
		startingRecord.setUserId(savedAccount.getUserId());
		startingRecord.setCategoryId(3); // 3 is for deposit
		startingRecord.setLabelId(1); // 1 test label
		
		
		 // Get the current system date
        LocalDate currentDate = LocalDate.now();

        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Format the date using the defined format
        String formattedDate = currentDate.format(formatter);
		
		startingRecord.setDate(formattedDate);
		
		
		
		
		
		 // Get the current system time
        LocalTime currentTime = LocalTime.now();

        // Define the time format
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        // Format the time using the defined format
        String formattedTime = currentTime.format(formatterTime);
		
		
		
		
		startingRecord.setTime(formattedTime);
		startingRecord.setPayer(savedAccount.getName());
		startingRecord.setPaymentTypeId(1); // 1 for cash
		startingRecord.setPaymentStatusId(2); // 2 for clear
		startingRecord.setNote("NEW ACCOUNT ADDED");
		startingRecord.setPlace("NO DATA");
		startingRecord.setAccountId(savedAccount.getId());
		startingRecord.setCurrencyId(savedAccount.getCurrencyId());
		startingRecord.setAmount(savedAccount.getAmount());
//		only required in case of transfer section
		startingRecord.setToAccountId(0);
		startingRecord.setToAmount(0);
		startingRecord.setToCurrencyId(0);
		recordService.saveRecord(startingRecord);
		System.out.println("savedAccount is " + savedAccount.getId());
		return "redirect:/Accounts";
	}

	@PostMapping("/accounts/edit")
	public String updateAccount(@ModelAttribute Account account) {
		accountService.save(account);
		return "redirect:/Accounts";
	}

	@PostMapping("/search")
	public String getAllAccountByName(@RequestParam("name") String name, Model model) {
		List<Account> account = accountService.getAccountByName(name);
		List<AccountType> accountType = accountService.getAllAccountType();
		List<Currency> currency = accountService.getAllCurrency();
		model.addAttribute("currency", currency);
		model.addAttribute("accounttypes", accountType);
		model.addAttribute("searchaccounts", account); // Add the list of states to the model
		model.addAttribute("AccountName", name); // Add the countryId to the model
		return "searcheditaccount"; // Assuming "state" is the name of your view/template
	}

	@GetMapping("/show")
	public String getAllStatesByCountryId(@RequestParam("id") int id, Model model) {
		List<Account> account = accountService.getAccountById(id);
		List<AccountType> accountType = accountService.getAllAccountType();
		List<Record> recordList = recordService.findByAccountId(id);
		JsonArray jsonArrayDate = new JsonArray();
		JsonArray jsonArrayAmount = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		recordList.forEach(data -> {
			jsonArrayDate.add(data.getDate());
			jsonArrayAmount.add(data.getAmount());
		});
		jsonObject.add("date", jsonArrayDate);
		jsonObject.add("amount", jsonArrayAmount);
		String linechart = jsonObject.toString();
		model.addAttribute("linechart", linechart);
		model.addAttribute("accounttypes", accountType);
		model.addAttribute("searchaccounts", account); // Add the list of states to the model
		model.addAttribute("AccountId", id); // Add the countryId to the model
		return "searchaccount";
	}
}
