package com.exavalu.budgetbakersb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Report;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.services.AccountService;
import com.exavalu.budgetbakersb.services.AccountService3;
import com.exavalu.budgetbakersb.services.RecordService3;
import com.exavalu.budgetbakersb.services.UserService2;
import com.google.gson.Gson;

@Controller
public class AnalyticController {

	@Autowired
	UserService2 userService2 = new UserService2();

	@Autowired
	AccountService accountService = new AccountService();

	@Autowired
	AccountService3 accountService3 = new AccountService3();

	@Autowired
	RecordService3 recordService3 = new RecordService3();

	@GetMapping("/Analytics")
	public String getAccounts(Model model, Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Account> accountList = accountService.getAllAccounts(user.getUserid());
		model.addAttribute("ACCOUNTLIST", accountList);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "analytics";
	}

	@GetMapping("/loadSidebar")
	public String getSideBar(Model model, Authentication authentication,
			@RequestParam(required = false) String reportLink, @RequestParam String accountId) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		System.out.println("load SideBar" + accountId);
		if (reportLink == null) {
			reportLink = "LoadIncomesAndExpenseReport";
		}
		model.addAttribute("REPORTLINK", reportLink);
		model.addAttribute("ACCOUNTID", accountId);
		List<Report> reportList = userService2.getAllReport();
		model.addAttribute("REPORTLIST", reportList);
		return "reports";
	}

	@GetMapping("/loadReports/")
	public String getReports(Model model, Authentication authentication, @RequestParam String reportLink,
			@RequestParam String accountId) {
		System.out.println("load reports" + accountId);
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		model.addAttribute("ACCOUNTID", accountId);
		if (accountId.equals("")) {
			return "analyticsSelectAccount";
		}
		return "redirect:/" + reportLink  + "?accountId=" + accountId;
	}

	@GetMapping("/LoadIncomesAndExpenseReport")
	public String getIncomesAndExpenseReport(Model model, Authentication authentication,
			@RequestParam String accountId) {
		System.out.println("LoadIncomesAndExpenseReport");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Category> categoryList = userService2.getAllCategories();
		model.addAttribute("CATEGORYLIST", categoryList);
		List<Double> cumulativeExpense = recordService3.getCumulativeExpense(user.getUserid(), accountId);
		model.addAttribute("CUMULATIVEEXPENSE", cumulativeExpense);
		double amount = accountService3.getTotalAmount(user.getUserid(), accountId);
		model.addAttribute("AMOUNT", amount);
		return "incomeExpenseReport";

	}

	@GetMapping("/LoadBalance")
	public String getBalance(Model model, Authentication authentication, @RequestParam String accountId) {
		return "error";
	}

	@GetMapping("/LoadCashFlow")
	public String getCashFlow(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadCashFlow");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> expenseList = recordService3.getExpenseList(user.getUserid(), accountId);
		List<Record> incomeList = recordService3.getIncomeList(user.getUserid(), accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		// Prepare data for expenses
		List<Map<String, Object>> expenseData = new ArrayList<>();
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			double amount = 0.0; // Default amount for the date if not found in expenseList
			for (Record expenseRecord : expenseList) {
				if (expenseRecord.getDate().equals(date)) {
					amount = expenseRecord.getAmount();
					break;
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("label", date);
			map.put("y", amount);
			expenseData.add(map);
		}

		// Prepare data for income
		List<Map<String, Object>> incomeData = new ArrayList<>();
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			double amount = 0.0; // Default amount for the date if not found in incomeList
			for (Record incomeRecord : incomeList) {
				if (incomeRecord.getDate().equals(date)) {
					amount = incomeRecord.getAmount();
					break;
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("label", date);
			map.put("y", amount);
			incomeData.add(map);
		}

		// Convert data to JSON strings
		Gson gson = new Gson();
		String expenseDataJson = gson.toJson(expenseData);
		String incomeDataJson = gson.toJson(incomeData);

		// Add JSON strings to model attributes
		model.addAttribute("expenseData", expenseDataJson);
		model.addAttribute("incomeData", incomeDataJson);

		return "cashFlow";
	}

	@GetMapping("/LoadCumulativeCashFlow")
	public String getCumulativeCashFlow(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadCumulativeCashFlow");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> expenseListCumulativeCashFlow = recordService3.getExpenseListCumulativeCashFlow(user.getUserid(),
				accountId);
		List<Record> incomeListCumulativeCashFlow = recordService3.getIncomeListCumulativeCashFlow(user.getUserid(),
				accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		List<Map<Object, Object>> expenseData = new ArrayList<Map<Object, Object>>();
		double amount1 = 0.0; // Default amount
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			for (Record expenseRecord : expenseListCumulativeCashFlow) {
				if (expenseRecord.getDate().equals(date)) {
					amount1 = expenseRecord.getAmount();
					break;
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", date);
			map.put("y", amount1);
			expenseData.add(map);
		}

		// Prepare data for income
		List<Map<Object, Object>> incomeData = new ArrayList<Map<Object, Object>>();
		double amount = 0.0; // Default amount
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			for (Record incomeRecord : incomeListCumulativeCashFlow) {
				if (incomeRecord.getDate().equals(date)) {
					amount = incomeRecord.getAmount();
					break;
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", date);
			map.put("y", amount);
			incomeData.add(map);
		}

		Gson gson = new Gson();
		String expenseDataJson = gson.toJson(expenseData);
		String incomeDataJson = gson.toJson(incomeData);

		// Add JSON strings to model attributes
		model.addAttribute("expenseData", expenseDataJson);
		model.addAttribute("incomeData", incomeDataJson);

		return "cumulativeCashFlow";
	}

	@GetMapping("/LoadOnlyIncome")
	public String getOnlyIncome(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadOnlyIncome");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> incomeList = recordService3.getIncomeList(user.getUserid(), accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		// Prepare data for income
		List<Map<String, Object>> incomeData = new ArrayList<>();
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			double amount = 0.0; // Default amount for the date if not found in incomeList
			for (Record incomeRecord : incomeList) {
				if (incomeRecord.getDate().equals(date)) {
					amount = incomeRecord.getAmount();
					break;
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("label", date);
			map.put("y", amount);
			incomeData.add(map);
		}

		// Convert data to JSON strings
		Gson gson = new Gson();
		String incomeDataJson = gson.toJson(incomeData);

		model.addAttribute("incomeData", incomeDataJson);
		return "income";
	}

	@GetMapping("/LoadExpense")
	public String getExpense(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadExpense");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> expenseList = recordService3.getExpenseList(user.getUserid(), accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		// Prepare data for expense
		List<Map<String, Object>> expenseData = new ArrayList<>();
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			double amount = 0.0; // Default amount for the date if not found in incomeList
			for (Record expenseRecord : expenseList) {
				if (expenseRecord.getDate().equals(date)) {
					amount = expenseRecord.getAmount();
					break;
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("label", date);
			map.put("y", amount);
			expenseData.add(map);
		}

		// Convert data to JSON strings
		Gson gson = new Gson();
		String expenseDataJson = gson.toJson(expenseData);

		model.addAttribute("expenseData", expenseDataJson);
		return "expense";
	}

	@GetMapping("/LoadCumulativeExpense")
	public String getCumulativeExpense(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadCumulativeExpense");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> expenseListCumulativeCashFlow = recordService3.getExpenseListCumulativeCashFlow(user.getUserid(),
				accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		List<Map<Object, Object>> expenseData = new ArrayList<Map<Object, Object>>();
		double amount1 = 0.0; // Default amount
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			for (Record expenseRecord : expenseListCumulativeCashFlow) {
				if (expenseRecord.getDate().equals(date)) {
					amount1 = expenseRecord.getAmount();
					break;
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", date);
			map.put("y", amount1);
			expenseData.add(map);
		}

		Gson gson = new Gson();
		String expenseDataJson = gson.toJson(expenseData);

		// Add JSON strings to model attributes
		model.addAttribute("expenseData", expenseDataJson);

		return "cumulativeExpense";
	}

	@GetMapping("/LoadCumulativeIncome")
	public String getCumulativeIncome(Model model, Authentication authentication, @RequestParam String accountId) {
		System.out.println("LoadCumulativeIncome");
		CustomUser user = (CustomUser) authentication.getPrincipal();
		model.addAttribute("users", user);
		List<Record> incomeListCumulativeCashFlow = recordService3.getIncomeListCumulativeCashFlow(user.getUserid(),
				accountId);
		List<Record> dateList = recordService3.getdateList(user.getUserid());

		// Prepare data for income
		List<Map<Object, Object>> incomeData = new ArrayList<Map<Object, Object>>();
		double amount = 0.0; // Default amount
		for (Record dateRecord : dateList) {
			String date = dateRecord.getDate();
			for (Record incomeRecord : incomeListCumulativeCashFlow) {
				if (incomeRecord.getDate().equals(date)) {
					amount = incomeRecord.getAmount();
					break;
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("label", date);
			map.put("y", amount);
			incomeData.add(map);
		}

		Gson gson = new Gson();
		String incomeDataJson = gson.toJson(incomeData);

		// Add JSON strings to model attributes
		model.addAttribute("incomeData", incomeDataJson);

		return "cumulativeIncome";
	}

}
