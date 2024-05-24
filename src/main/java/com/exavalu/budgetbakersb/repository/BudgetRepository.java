package com.exavalu.budgetbakersb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.Budget;



public interface BudgetRepository  extends JpaRepository<Budget, Integer>{
	 @Query("SELECT b FROM Budget b JOIN Category c ON b.categoryId = c.categoryId")
	    List<Budget> findAllBudgetsWithCategory();
	 
	 @Query("SELECT c.categoryName, b.budgetAmount, COALESCE(SUM(r.amount) * -1, 0) " +
	           "FROM Budget b " +
	           "LEFT JOIN Category c ON b.category.categoryId = c.categoryId " +
	           "LEFT JOIN Record r ON b.category.categoryId = r.category.categoryId " +
	           "WHERE c.recordTypeId = ?1 " +
	           "AND r.userId = ?2 " +
	           "AND r.date BETWEEN ?3 AND ?4 " +
	           "GROUP BY c.categoryName, b.budgetAmount")
	    List<Object[]> findAllBudgetsWithCategoryAndActualExpenses(@Param("recordTypeId") int recordTypeId, @Param("userId") int userId, @Param("firstDayOfMonth") String firstDayOfMonth, @Param("lastDayOfMonth") String lastDayOfMonth);
}