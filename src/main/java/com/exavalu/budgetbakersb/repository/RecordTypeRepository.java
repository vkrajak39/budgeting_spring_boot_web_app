package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.RecordType;
import com.exavalu.budgetbakersb.entity.RecordTypeEnum;

public interface RecordTypeRepository extends JpaRepository<RecordType, Integer> {
	@Query("SELECT rt.id FROM RecordType rt WHERE rt.recordTypeName = ?1")
	int findIdByRecordTypeName(@Param("name") RecordTypeEnum name);
}
