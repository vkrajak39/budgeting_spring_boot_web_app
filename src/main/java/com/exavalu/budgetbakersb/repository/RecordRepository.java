package com.exavalu.budgetbakersb.repository;

import com.exavalu.budgetbakersb.entity.Record; // Import the Record entity
import com.exavalu.budgetbakersb.entity.RecordType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Integer> {

	// Account Service STR_TO_DATE(r1_0.date, '%d/%m/%Y')
	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.userId = ?1 AND r.recordTypeId = ?2 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?3, '%d/%m/%Y') AND STR_TO_DATE(?4, '%d/%m/%Y') AND r.accountId = ?5")
	double sumAmountByUserIdAndRecordTypeAndDateBetweenAndAccountId(int userId, int recordTypeId, String date1,
			String date2, int accountId);

	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.toAccountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = ?1) AND r.userId = ?1 AND r.recordTypeId =3 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y') AND r.accountId = ?4")
	double sumNegativeTransferAmount(int userId, String givenDate1, String givenDate2, int accountId);

	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r "
			+ "WHERE r.toAccountId IN (SELECT a.id FROM Account a WHERE a.userId = ?1) "
			+ "AND r.accountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = ?1) "
			+ "AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y') " + "AND r.accountId = ?4")
	double sumIncomeTransferAmount(int userId, String givenDate1, String givenDate2, int accountId);

	@Query("SELECT c.categoryName, "
			+ "COALESCE(SUM(r.amount), 0) / (SELECT COALESCE(SUM(r2.amount), 1) FROM Record r2 WHERE r2.userId = ?1 and accountId=?4 AND r2.recordTypeId = 1 "
			+ "AND STR_TO_DATE(r2.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y')) * 100 AS percentageExpense " + "FROM Record r " + "JOIN r.category c "
			+ "WHERE r.userId = ?1 and accountId=?4 AND r.recordTypeId = 1 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y') "
			+ "GROUP BY c.categoryName")
	List<Object[]> getAccountExpenseCategoryPercentage(int userId, String givenDate1, String givenDate2, int accId);

	// Method to get records of a user for a specific account within a date range
	@Query("SELECT r FROM Record r WHERE r.userId = :userId AND r.accountId = :accountId AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(:date1, '%d/%m/%Y') AND STR_TO_DATE(:date2, '%d/%m/%Y')")
	List<Record> getRecordOfUserAccordingToAccountIdDateRange(@Param("userId") int userId,
			@Param("accountId") int accountId, @Param("date1") String date1, @Param("date2") String date2);

	// Method to get balance of a user for a specific account within a date range
	@Query("SELECT date, " + "(income + incomeTransfer + negativeTransfer + expense+initialAmount) as balance FROM("
			+ "Select r.date as date," + "SUM(CASE WHEN r.recordTypeId = 1 THEN r.amount ELSE 0 END) AS expense, "
			+ "SUM(CASE WHEN r.recordTypeId = 2 THEN r.amount ELSE 0 END) AS income, "
			+ "SUM(CASE WHEN r.recordTypeId = 3 AND r.toAccountId NOT IN "
			+ "(SELECT a.id FROM Account a WHERE a.userId = :userId) THEN r.amount ELSE 0 END) AS negativeTransfer, "
			+ "SUM(CASE WHEN r.recordTypeId = 3 AND r.toAccountId IN "
			+ "(SELECT a.id FROM Account a WHERE a.userId = :userId) AND r.accountId NOT IN "
			+ "(SELECT a.id FROM Account a WHERE a.userId = :userId) THEN r.amount ELSE 0 END) "
			+ "AS incomeTransfer,"
			+ "SUM(CASE WHEN r.recordTypeId = 4 THEN r.amount ELSE 0 END) AS initialAmount "
			+ " FROM Record r WHERE r.userId = :userId AND r.accountId = :accountId "
			+ "GROUP BY r.date) as subquery" + " where STR_TO_DATE(date, '%d/%m/%Y') <= STR_TO_DATE(:date1, '%d/%m/%Y')")
	List<Object[]> getAccountBalanceAtDateRange(@Param("userId") int userId, @Param("accountId") int accountId,
			@Param("date1") String date);
	
	@Query("SELECT SUM(r.amount) FROM Record r WHERE r.userId = ?1 AND r.recordTypeId = ?2 AND r.accountId=?3")
    double sumAccountInitialAmount(int userId, int recTypeId, int accId);
	

	// User Service

	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.userId = ?1 AND r.recordTypeId = ?2 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?3, '%d/%m/%Y') AND STR_TO_DATE(?4, '%d/%m/%Y')")
	double sumAmountByUserIdAndRecordTypeAndDateBetween(int userId, int recordType, String date1, String date2);

	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.toAccountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = ?1) AND r.userId = ?1 AND r.recordTypeId = 3 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y')")
	double sumNegativeTransferAmount(int userId, String givenDate1, String givenDate2);

	@Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.toAccountId IN (SELECT a.id FROM Account a WHERE a.userId = ?1) AND r.accountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = ?1) AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y')")
	double sumIncomeTransferAmount(int userId, String givenDate1, String givenDate2);

	@Query("SELECT c.categoryName, "
			+ "COALESCE(SUM(r.amount), 0) / (SELECT COALESCE(SUM(r2.amount), 1) FROM Record r2 WHERE r2.userId = ?1 AND r2.recordTypeId =1 AND STR_TO_DATE(r2.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y')) * 100 AS percentageExpense "
			+ "FROM Record r JOIN r.category c "
			+ "WHERE r.userId = ?1 AND r.recordTypeId = 1 AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?2, '%d/%m/%Y') AND STR_TO_DATE(?3, '%d/%m/%Y') " + "GROUP BY c.categoryName")
	List<Object[]> getExpenseCategoryPercentage(int userId, String givenDate1, String givenDate2);

	// Method to get records of a user within a date range
	@Query("SELECT r FROM Record r WHERE r.userId = :userId AND STR_TO_DATE(r.date, '%d/%m/%Y') BETWEEN STR_TO_DATE(:date1, '%d/%m/%Y') AND STR_TO_DATE(:date2, '%d/%m/%Y')")
	List<Record> getRecordOfUserDateRange(@Param("userId") int userId, @Param("date1") String date1,
			@Param("date2") String date2);

	// Method to get balance of a user within a date range
	@Query("SELECT date, "
			//+ "expense,income,negativeTransfer,incomeTransfer,initialAmount," 
			+ "(income + incomeTransfer + negativeTransfer + expense+initialAmount) as balance FROM("
			+ "Select r.date as date," + "SUM(CASE WHEN r.recordTypeId = 1 THEN r.amount ELSE 0 END) AS expense , "
			+ "SUM(CASE WHEN r.recordTypeId = 2 THEN r.amount ELSE 0 END) AS income , "
			+ "SUM(CASE WHEN r.recordTypeId = 3 AND r.toAccountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = :userId) "
			+ "THEN r.amount ELSE 0 END) AS negativeTransfer , "
			+ "SUM(CASE WHEN r.recordTypeId = 3 AND r.toAccountId IN (SELECT a.id FROM Account a WHERE a.userId = :userId) "
			+ "AND r.accountId NOT IN (SELECT a.id FROM Account a WHERE a.userId = :userId) "
			+ "THEN r.amount ELSE 0 END) AS incomeTransfer ,"
			+ "SUM(CASE WHEN r.recordTypeId = 4 THEN r.amount ELSE 0 END) AS initialAmount "
			+ "FROM Record r WHERE r.userId = :userId GROUP BY r.date) as subquery" + " where STR_TO_DATE(date, '%d/%m/%Y') <= STR_TO_DATE(:date1, '%d/%m/%Y')")
	List<Object[]> getBalanceAtDateRange(@Param("userId") int userId, @Param("date1") String date1);
    
	@Query("SELECT SUM(r.amount) FROM Record r WHERE r.userId = ?1 AND r.recordTypeId = ?2")
	double sumInitialAmount(int userId,int recTypeId);

	List<Record> findByAccountId(int accountId);

	// return list of records of user
	List<Record> findByUserId(int userId);
	
	@Query("SELECT c.categoryId, c.categoryName, ABS(COALESCE(SUM(CASE WHEN r.recordTypeId = 1 THEN r.amount ELSE 0 END), 0)) AS cumulativeExpense FROM Category c LEFT JOIN Record r ON c.categoryId = r.categoryId AND r.userId = ?1 AND r.accountId IN (?2) GROUP BY c.categoryId, c.categoryName")
	List<Object[]> findCumulativeExpenseByUserIdAndAccountId(int userid, List<Integer> accountIds);
	
	
	@Query("SELECT r.date AS date, SUM(ABS(r.amount)) AS amount FROM Record r WHERE r.recordTypeId = 1 AND r.userId = ?1 AND r.accountId IN (?2) GROUP BY r.date ORDER BY r.date")
	List<Object[]> findExpenseListByUserIdAndAccountId(int userid, List<Integer> accountIds);
	 
	@Query("SELECT r.date AS date, SUM(ABS(r.amount)) AS amount FROM Record r WHERE r.recordTypeId = 2 AND r.userId = ?1 AND r.accountId IN (?2) GROUP BY r.date ORDER BY r.date")
	List<Object[]> findIncomeListByUserIdAndAccountId(int userid, List<Integer> accountIds);
	
	@Query("SELECT DISTINCT r.date FROM Record r WHERE r.userId = ?1 ORDER BY STR_TO_DATE(r.date, '%d/%m/%y') ASC")
    List<Object[]> findDateListByUserId(int userid);

    @Query("SELECT r.date AS date, SUM(SUM(ABS(r.amount))) OVER (ORDER BY STR_TO_DATE(r.date, '%d/%m/%y')) AS amount " +
            "FROM Record r WHERE r.recordTypeId = 1 AND r.userId = ?1 AND r.accountId IN (?2) " +
            "GROUP BY r.date ORDER BY STR_TO_DATE(r.date, '%d/%m/%y')")
    List<Object[]> findExpenseListCumulativeCashFlowByUserIdAndAccountId(int userid, List<Integer> accountIdList);
    
    @Query("SELECT r.date AS date, SUM(SUM(ABS(r.amount))) OVER (ORDER BY STR_TO_DATE(r.date, '%d/%m/%y')) AS amount " +
            "FROM Record r WHERE r.recordTypeId = 2 AND r.userId = ?1 AND r.accountId IN (?2) " +
            "GROUP BY r.date ORDER BY STR_TO_DATE(r.date, '%d/%m/%y')")
	List<Object[]> findIncomeListCumulativeCashFlowByUserIdAndAccountId(int userid, List<Integer> accountIdList);


	
//	filter records
	
	
/*
	List<Record> findByUserIdAndAccountIdIn(int userId, List<Integer> accountCheckedList);

	List<Record> findByUserIdAndCurrencyIdIn(int userId, List<Integer> currencyCheckedList);

	List<Record> findByUserIdAndCategoryIdIn(int userId, List<Integer> categoryCheckedList);

	List<Record> findByUserIdAndPaymentStatusIdIn(int userId, List<Integer> paymentStatusCheckedList);

	List<Record> findByUserIdAndPaymentTypeIdIn(int userId, List<Integer> paymentTypeCheckedList);

	List<Record> findByUserIdAndLabelIdIn(int userId, List<Integer> listCheckedList);

	List<Record> findByUserIdAndAccountIdInAndCurrencyIdIn(int userId, List<Integer> accountCheckedList,
			List<Integer> currencyCheckedList);

	List<Record> findByUserIdAndAccountIdInAndCategoryIdIn(int userId, List<Integer> accountCheckedList,
			List<Integer> categoryCheckedList);

	List<Record> findByUserIdAndAccountIdInAndPaymentStatusIdIn(int userId, List<Integer> accountCheckedList,
			List<Integer> paymentStatusCheckedList);

	List<Record> findByUserIdAndAccountIdInAndPaymentTypeIdIn(int userId, List<Integer> accountCheckedList,
			List<Integer> paymentTypeCheckedList);

	List<Record> findByUserIdAndAccountIdInAndLabelIdIn(int userId, List<Integer> accountCheckedList,
			List<Integer> listCheckedList);

	List<Record> findByUserIdAndCurrencyIdInAndCategoryIdIn(int userId, List<Integer> currencyCheckedList,
			List<Integer> categoryCheckedList);

	List<Record> findByUserIdAndCurrencyIdInAndPaymentStatusIdIn(int userId, List<Integer> currencyCheckedList,
			List<Integer> paymentStatusCheckedList);

	List<Record> findByUserIdAndCurrencyIdInAndPaymentTypeIdIn(int userId, List<Integer> currencyCheckedList,
			List<Integer> paymentTypeCheckedList);

	List<Record> findByUserIdAndCurrencyIdInAndLabelIdIn(int userId, List<Integer> currencyCheckedList,
			List<Integer> listCheckedList);

	List<Record> findByUserIdAndCategoryIdInAndPaymentStatusIdIn(int userId, List<Integer> categoryCheckedList,
			List<Integer> paymentStatusCheckedList);

	List<Record> findByUserIdAndCategoryIdInAndPaymentTypeIdIn(int userId, List<Integer> categoryCheckedList,
			List<Integer> paymentTypeCheckedList);

	List<Record> findByUserIdAndCategoryIdInAndLabelIdIn(int userId, List<Integer> categoryCheckedList,
			List<Integer> listCheckedList);

	List<Record> findByUserIdAndPaymentStatusIdInAndPaymentTypeIdIn(int userId, List<Integer> paymentStatusCheckedList,
			List<Integer> paymentTypeCheckedList);

	List<Record> findByUserIdAndPaymentStatusIdInAndLabelIdIn(int userId, List<Integer> paymentStatusCheckedList,
			List<Integer> listCheckedList);

	List<Record> findByUserIdAndPaymentTypeIdInAndLabelIdIn(int userId, List<Integer> paymentTypeCheckedList,
			List<Integer> listCheckedList);

	*/		

//	@Query("SELECT r FROM Record r WHERE " +
//		       "((:accountIds IS NULL OR :accountIds = [] OR r.accountId = :accountIds) AND (:currencyIds IS NOT NULL AND :currencyIds != [] AND r.currencyId = :currencyIds)) OR " +
//		       "((:currencyIds IS NULL OR :currencyIds = [] OR r.currencyId = :currencyIds) AND (:accountIds IS NOT NULL AND :accountIds != [] AND r.accountId = :accountIds)) OR " +
//		       "(:accountIds IS NULL OR :accountIds = [] AND :currencyIds IS NULL OR :currencyIds = [])")
//		List<Record> findRecordsByAccountIdsAndCurrencyIds(@Param("accountIds") List<Integer> accountCheckedList, @Param("currencyIds") List<Integer> currencyCheckedList);	
//	@Query("SELECT e FROM record e " +
//	           "WHERE (:accountIds IS NULL OR e.accountId IN (:accountIds)) " +
//	           "AND (:currencyIds IS NULL OR e.currencyId IN (:currencyIds))")
//	List<Record> getFilteredDataTest( List<Integer> accountIds,
//             List<Integer> currencyIds);
//	
	

//	@Query(value = "SELECT * FROM record WHERE 1=1 AND  (CASE WHEN ?1 IS NOT EMPTY THEN account_id IN ?1 ELSE 1=1 END) AND (CASE WHEN ?2 IS NOT EMPTY THEN category_id IN ?2 ELSE 1=1 END) AND (CASE WHEN ?3 IS NOT EMPTY THEN currency_id IN ?3 ELSE 1=1 END) AND (CASE WHEN ?4 IS NOT EMPTY THEN payment_type_id IN ?4 ELSE 1=1 END) AND (CASE WHEN ?5 IS NOT EMPTY THEN payment_status_id IN ?5 ELSE 1=1 END) AND (CASE WHEN ?6 IS NOT EMPTY THEN label_id IN ?6 ELSE 1=1 END) ", nativeQuery = true)
//	List<Record> getFilteredData(List<Integer> accountCheckedList, List<Integer> categoryCheckedList,
//			List<Integer> currencyCheckedList, List<Integer> paymentTypeCheckedList,
//			List<Integer> paymentStatusCheckedList, List<Integer> listCheckedList);
	
	
	
//	 @Query("SELECT e FROM record e " +
//	           "WHERE (:accountIds IS NULL OR e.accountId IN (:accountIds)) " +
//	           "AND (:currencyIds IS NULL OR e.currencyId IN (:currencyIds))")
//	    List<Record> getFilteredDataTest(@Param("accountCheckedList") List<Integer> accountIds,
//	                                   @Param("categoryCheckedList") List<Integer> currencyIds);
	
//	@Query(value = "SELECT * FROM record WHERE 1=1 AND (?1 IS NULL OR account_id IN ?1) ", nativeQuery = true)
//	List<Record> getFilteredDataTest(List<Integer> accountCheckedList);

	
	
}
