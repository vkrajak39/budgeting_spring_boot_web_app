package com.exavalu.budgetbakersb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exavalu.budgetbakersb.config.CustomUser;
import com.exavalu.budgetbakersb.entity.Budget;
import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Menu;
import com.exavalu.budgetbakersb.entity.User;
import com.exavalu.budgetbakersb.services.BudgetService;
import com.exavalu.budgetbakersb.services.MenuService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

@Controller
public class BudgetController {
	
	@Autowired
	BudgetService budgetService;
	
	@Autowired
	public MenuService menuService;
	
	@GetMapping("/AdjustBudget")
	public String adjustBudgetRoute(HttpSession session,Model model, Authentication authentication) {
		List<Budget> budgetArray = budgetService.getAllBudgetsWithCategory();
		
		for (Budget budget : budgetArray) {
            Category category = budget.getCategory();
            
            System.out.println("Budget ID: " + budget.getId());
            System.out.println("Budget Amount: " + budget.getBudgetAmount());
            System.out.println("Category ID: " + category.getCategoryId());
            System.out.println("Category Name: " + category.getCategoryName());
            System.out.println("------------------------------------");
        }
		model.addAttribute("budgetArray",budgetArray);
		return "adjustBudget";
	}
	
	@PostMapping("/updateBudget")
    public String updateBudgetAmount(@RequestParam("budgetId") int budgetId,
                                     @RequestParam("newAmount") double newAmount) {
        budgetService.updateBudgetAmount(budgetId, newAmount);
        return "redirect:/AdjustBudget";
    }
	
	@GetMapping("/BudgetVSActual")
	public String budgetvsactualRoute(HttpSession session,Model model,Authentication authentication) {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		int recordTypeID = 1;
		User user = customUser.getUser();
		List<Menu> menu = menuService.prepareMenus(user.getUserRoleId());
		session.setAttribute("MENU", menu);
		List<Object[]> budgetDetails = budgetService.getAllBudgetsWithCategoryAndActualExpenses(recordTypeID, customUser.getUserid());
		for (Object[] detail : budgetDetails) {
            for (Object value : detail) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
		model.addAttribute("budgetDetails", budgetDetails);
		return "budgetvsactual";
	}
	
	
}
