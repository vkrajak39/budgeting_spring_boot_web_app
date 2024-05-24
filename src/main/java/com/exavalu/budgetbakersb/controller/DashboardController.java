package com.exavalu.budgetbakersb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.entity.Menu;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.services.AccountService;
import com.exavalu.budgetbakersb.services.AccountService2;
import com.exavalu.budgetbakersb.services.MenuService;
import com.exavalu.budgetbakersb.services.RecordService2;
import com.exavalu.budgetbakersb.services.RecordServices;
import com.exavalu.budgetbakersb.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@Autowired
	public UserService userService;

	@Autowired
	public RecordService2 recordService2;

	@Autowired
	public AccountService2 accountService2;

	@Autowired
	public MenuService menuService;

	@Autowired
	RecordServices recordService = new RecordServices();

	@Autowired
	AccountService accountService;

	@GetMapping("/dummy")
	public String dummy(HttpSession session, Authentication authentication, Model model) {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		User user = customUser.getUser();
		session.setAttribute("USER", user);
		System.out.println("b");

		return "dummy";
	}

	@PostMapping("/addFirstAccount")
	public String addAccounts(@RequestParam String userId, @RequestParam String accountName,
			@RequestParam String accountType, @RequestParam String amount, @RequestParam String currency)

	{

		Account account = new Account();
		account.setUserId(Integer.parseInt(userId));
		account.setName(accountName);
		account.setAccountTypeId(Integer.parseInt(accountType));
		account.setAmount(Integer.parseInt(amount));
		account.setCurrencyId(Integer.parseInt(currency));
		Account savedAccount = accountService.save(account);
		Record startingRecord = new Record();
		startingRecord.setRecordTypeId(4); // 4 is for starting
		startingRecord.setUserId(savedAccount.getUserId());
		startingRecord.setCategoryId(3); // 3 is for deposit
		startingRecord.setLabelId(1); // 1 test label

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(cal.getTime());

		startingRecord.setDate(currentDate);

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
		return "redirect:/Dashboard";
	}

	@GetMapping("/Dashboard")
	public String dashboard(HttpSession session, Model model, Authentication authentication,
			@RequestParam(defaultValue = "") String date) {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		User user = customUser.getUser();
		if (user != null) {
			session.setAttribute("USER", user);
			List<Menu> menu = menuService.prepareMenus(user.getUserRoleId());
			session.setAttribute("MENU", menu);
			List<AccountType> accountType = accountService.getAllAccountType();
			model.addAttribute("accounttypes", accountType);
			List<Currency> currency = accountService.getAllCurrency();
			model.addAttribute("currency", currency);

			List<Account> account = accountService.getAllAccounts(user.getUserid());
			if (account.size() == 0)
				return "Dashboard/dashboardEmpty";

			session.setAttribute("ACCOUNT", account);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String currentDate = dateFormat.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month
			String thisMonthFirstDay = dateFormat.format(cal.getTime()); // Format it
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to last day of the month
			String thisMonthLastDay = dateFormat.format(cal.getTime()); // Format it

			// Get parameters from request if needed
			String[] arr = date != "" ? date.split("-") : new String[0];
			String date1 = "", date2 = "";
			if (arr.length == 2) {
				date1 = arr[0].trim();
				date2 = arr[1].trim();
			}

			if (date1.isEmpty() || date2.isEmpty()) {
				date1 = thisMonthFirstDay;
				date2 = thisMonthLastDay;
			}

			// Retrieve data using UserService methods

			double expense = userService.getUserExpense(user, date1, date2);
			double income = userService.getUserIncome(user, date1, date2);
			double transfer = userService.getUserNegativeTransfer(user, date1, date2);
			int findTotalAccounts = accountService.getTotalNumberOfAccounts(user.getUserid());
			double initialAmount;
			if (findTotalAccounts != 0) {
				initialAmount = userService.getUserInitialAmount(user);
			} else {
				initialAmount = 0.0;
			}
			double incomeTransfer = userService.getUserIncomeTransfer(user, date1, date2);
			HashMap<String, Double> expenseCategory = userService.getExpenseCategoryPercentage(user, date1, date2);
			// You may want to handle exceptions here

			// Retrieve recordList2 and balanceList2
			List<Record> recordList2 = recordService2.getRecordOfUserDateRange(user.getUserid(), date1, date2);
			List<Object[]> balanceList2 = recordService2.getBalanceAtDateRange(user.getUserid(), date2);
			List<Object[]> balanceOfuser = new ArrayList<>(); // Initialize the list
			// Calculate balance
			double balance =0.0; int f=0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {

				Date endDate = sdf.parse(date2);
				for (Object[] row : balanceList2) {
					Date dateA = sdf.parse((String) row[0]);
					System.out.println(dateA+" "+row[0]+" "+row[1]);
					if (!dateA.after(endDate)) {
						balance += (double) row[1];
						balanceOfuser.add(new Object[] { row[0], balance }); // Correct way to add elements

					} else {
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				// Handle parsing exception
			}

			// Set attributes in session or model
			session.setAttribute("EXPENSEUSER", expense);
			session.setAttribute("INCOMEUSER", income);
			session.setAttribute("TRANSFERUSER", transfer);
			session.setAttribute("INITIALAMTUSER", initialAmount);
			session.setAttribute("INCOMETRANSFER", incomeTransfer);
			session.setAttribute("DATE", date1 + " - " + date2);
			session.setAttribute("EXPENSECATEGORY", expenseCategory);
			session.setAttribute("RECORDLIST2", recordList2);
			session.setAttribute("BALANCELIST2", balanceOfuser);
			session.setAttribute("BALANCE", balance);
			session.setAttribute("ACCOUNTDASHBOARD", 0);
//			System.out.println(session.getAttribute("RECORDLIST2"));

			return "Dashboard/dashboard";
		} else {
			return "redirect:/signin"; // Redirect to login page
		}
	}

	@GetMapping("/DashboardAccount")
	public String dashboardAccount(HttpSession session, Model model, Authentication authentication,
			@RequestParam(defaultValue = "") String date, @RequestParam int id) {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		User user = customUser.getUser();
		if (user != null) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String currentDate = dateFormat.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month
			String thisMonthFirstDay = dateFormat.format(cal.getTime()); // Format it
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to last day of the month
			String thisMonthLastDay = dateFormat.format(cal.getTime()); // Format it


			// Get parameters from request if needed
			String[] arr = date != "" ? date.split("-") : new String[0];
			String date1 = "", date2 = "";
			if (arr.length == 2) {
				date1 = arr[0].trim();
				date2 = arr[1].trim();
			}

			if (date1.isEmpty() || date2.isEmpty()) {
				date1 = thisMonthFirstDay;
				date2 = thisMonthLastDay;
			}
//			System.out.println(date1+" "+date2);

			// Retrieve data using UserService methods

			double expense = accountService2.getAccountExpense(user, date1, date2, id);
			double income = accountService2.getAccountIncome(user, date1, date2, id);
			double transfer = accountService2.getAccountNegativeTransfer(user, date1, date2, id);
			double initialAmount = accountService2.getAccountInitialAmount(user, id);
			double incomeTransfer = accountService2.getAccountIncomeTransfer(user, date1, date2, id);
			HashMap<String, Double> expenseCategory = accountService2.getAccountExpenseCategoryPercentage(user, date1,
					date2, id);
			// You may want to handle exceptions here

			// Retrieve recordList2 and balanceList2
			List<Record> recordList2 = recordService2.getRecordOfUserAccordingToAccountIdDateRange(user.getUserid(), id,
					date1, date2);
			List<Object[]> balanceList2 = recordService2.getAccountBalanceAtDateRange(user.getUserid(), id, date2);
			List<Object[]> balanceOfuser = new ArrayList<>(); // Initialize the list
			// Calculate balance
			double balance = 0.0;int f=0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {

				Date endDate = sdf.parse(date2);
				for (Object[] row : balanceList2) {
					Date dateA = sdf.parse((String) row[0]);
					if (!dateA.after(endDate)) {
											balance += (double) row[1];
						balanceOfuser.add(new Object[] { row[0], balance }); // Correct way to add elements

					} else {
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				// Handle parsing exception
			}

			// Set attributes in session or model
			session.setAttribute("USER", user);
			session.setAttribute("EXPENSEUSER", expense);
			session.setAttribute("INCOMEUSER", income);
			session.setAttribute("TRANSFERUSER", transfer);
			session.setAttribute("INITIALAMTUSER", initialAmount);
			session.setAttribute("INCOMETRANSFER", incomeTransfer);
			session.setAttribute("DATE", date1 + " - " + date2);
			session.setAttribute("EXPENSECATEGORY", expenseCategory);
			session.setAttribute("RECORDLIST2", recordList2);
			session.setAttribute("BALANCELIST2", balanceOfuser);
			session.setAttribute("BALANCE", balance);
			session.setAttribute("ACCOUNTDASHBOARD", id);
//			System.out.println(session.getAttribute("EXPENSECATEGORY"));

			List<Account> account = accountService.getAllAccounts(user.getUserid());
			session.setAttribute("ACCOUNT", account);
			List<Menu> menu = menuService.prepareMenus(user.getUserRoleId());
			session.setAttribute("MENU", menu);
			List<AccountType> accountType = accountService.getAllAccountType();
			model.addAttribute("accounttypes", accountType);
			List<Currency> currency = accountService.getAllCurrency();
			model.addAttribute("currency", currency);

			return "Dashboard/dashboard";
		} else {
			return "redirect:/signin"; // Redirect to login page
		}
	}
}
