package com.exavalu.budgetbakersb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.Category;

import jakarta.transaction.Transactional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@Query("SELECT c.id FROM Category c WHERE c.categoryName = ?1")
	int findIdByCategoryName(@Param("categoryName") String categoryName);

	@Query("SELECT c.categoryId, c.categoryName, c.recordTypeId, " + "CASE WHEN c.recordTypeId = 1 THEN 'Expense' "
			+ "WHEN c.recordTypeId = 2 THEN 'Income' WHEN c.recordTypeId = 3 THEN 'TRANSFER' WHEN c.recordTypeId = 4 THEN 'STARTING' ELSE 'Unknown' END AS recordTypeName "
			+ "FROM Category c")
	List<Object[]> findCategoryForReadCategory();
	
	@Modifying
	@Query("INSERT INTO Category (categoryName, recordTypeId) VALUES (:categoryName, :recordTypeId)")
	int addCategoryForReadCategory(@Param("categoryName") String categoryName, @Param("recordTypeId") Integer recordTypeId);

	 @Modifying
	 @Query("UPDATE Category c SET c.categoryName = ?2, c.recordTypeId = ?3 WHERE c.categoryId = ?1")
	 int updateCategoryForEditCategory(int categoryId, String categoryName, int recordType);
	 
}