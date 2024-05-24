package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.PaymentStatus;
import com.exavalu.budgetbakersb.entity.PaymentStatusEnum;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer> {
	@Query("SELECT ps.id FROM PaymentStatus ps WHERE ps.name = ?1")
    int findIdByPaymentStatusName(@Param("name") PaymentStatusEnum name);
}
