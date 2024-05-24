package com.exavalu.budgetbakersb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.exavalu.budgetbakersb.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByName(String name);
	List<Account> findByUserId(int userId);
	List<Account> findById(int id);
	
	
	@Query("SELECT a.id FROM Account a WHERE a.name = ?1")
    int findIdByAccountName(@Param("name") String name);
	
	@Query("SELECT SUM(a.amount) FROM Account a WHERE a.userId = ?1")
	double findTotalAmountByUserId(int userid);
	
	 @Query("SELECT SUM(a.amount) FROM Account a WHERE a.userId = ?1 AND a.id IN (?2)")
	double findTotalAmountByUserIdAndAccountId(int userid, List<Integer> accountIds);
	 
	 @Query("SELECT COUNT(*) FROM Account WHERE userId = ?1")
	 int getTotalNumberOfAccounts(int userId);

	 	
}
