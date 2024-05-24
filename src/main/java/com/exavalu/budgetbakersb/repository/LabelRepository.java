package com.exavalu.budgetbakersb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.Label;

import jakarta.transaction.Transactional;

@Transactional
public interface LabelRepository extends JpaRepository<Label, Integer>{
	@Query("SELECT l.id FROM Label l WHERE l.labelName = ?1")
    Integer findIdByLabelName(@Param("labelName") String labelName);
	
	@Query("SELECT l.labelId, l.labelName, c.categoryName, c.categoryId FROM Label l JOIN Category c ON l.categoryId = c.categoryId")
    List<Object[]> findLabelForReadLabel();
    
    @Modifying
    @Query("INSERT INTO Label(labelName, categoryId) VALUES (?1, ?2)")
    int addLabelForReadLabel(String labelName, int category);
    
   
    @Modifying
    @Query("UPDATE Label l SET l.labelName = ?2, l.categoryId = ?3 WHERE l.labelId = ?1")
	int updateLabelForEditLabel(int labelId, String labelName, int category);
	
}

