package com.exavalu.budgetbakersb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exavalu.budgetbakersb.entity.PaymentType;

import jakarta.transaction.Transactional;

@Transactional
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
	@Query("SELECT pt.id FROM PaymentType pt WHERE pt.name = :name")
	int findIdByPaymentTypeName(@Param("name") String name);

	@Query(value = "SELECT * FROM payment_type", nativeQuery = true)
	List<Object[]> findPaymentTypeForReadPaymentType();

	@Modifying
	@Query("UPDATE PaymentType pt SET pt.name = ?2 WHERE pt.id = ?1")
	int updatePaymentTypeForPaymentType(int paymentTypeId, String paymentTypeName);

	@Modifying
	@Query("INSERT INTO PaymentType(name) VALUES (?1)")
	int addPaymentTypeForCreatePaymentType(String paymentTypeName);

}
