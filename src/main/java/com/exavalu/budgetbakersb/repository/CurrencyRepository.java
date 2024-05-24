package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.entity.CurrencyCodeEnum;
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer>{
	@Query("SELECT c.id FROM Currency c WHERE c.currencyCode = ?1")
    int findIdByCurrencyName(@Param("currencyCode") CurrencyCodeEnum currencyCode);
}