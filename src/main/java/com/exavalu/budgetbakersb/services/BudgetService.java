package com.exavalu.budgetbakersb.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.entity.Budget;
import com.exavalu.budgetbakersb.repository.BudgetRepository;

@Service
public class BudgetService {
	
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	public List<Budget> getAllBudgetsWithCategory() {
        return budgetRepository.findAllBudgetsWithCategory();
    }
	
	public void updateBudgetAmount(int budgetId, double newAmount) {
        Optional<Budget> optionalBudget = budgetRepository.findById(budgetId);
        if (optionalBudget.isPresent()) {
            Budget budget = optionalBudget.get();
            budget.setBudgetAmount(newAmount);
            budgetRepository.save(budget);
        } else {
            // Handle error: budget not found
        }
    }
	
	
	public List<Object[]> getAllBudgetsWithCategoryAndActualExpenses(int recordTypeId, int userId) {
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        Date firstDayOfMonth = calendar.getTime();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDay); // Set to the last day of the month
        Date lastDayOfMonth = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String firstDayOfMonthString = dateFormat.format(firstDayOfMonth);
        String lastDayOfMonthString = dateFormat.format(lastDayOfMonth);
        return budgetRepository.findAllBudgetsWithCategoryAndActualExpenses(recordTypeId, userId,firstDayOfMonthString,lastDayOfMonthString);
    }
}
