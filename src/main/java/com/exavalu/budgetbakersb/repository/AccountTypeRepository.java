package com.exavalu.budgetbakersb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exavalu.budgetbakersb.entity.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>{

}
