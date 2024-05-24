package com.exavalu.budgetbakersb.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.exavalu.budgetbakersb.entity.Account;
import com.exavalu.budgetbakersb.entity.AccountType;
import com.exavalu.budgetbakersb.entity.Category;
import com.exavalu.budgetbakersb.entity.Currency;
import com.exavalu.budgetbakersb.entity.Label;
import com.exavalu.budgetbakersb.entity.PaymentStatus;
import com.exavalu.budgetbakersb.entity.PaymentType;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.entity.RecordType;
import com.exavalu.budgetbakersb.repository.AccountRepository;
import com.exavalu.budgetbakersb.repository.AccountTypeRepository;
import com.exavalu.budgetbakersb.repository.CategoryRepository;
import com.exavalu.budgetbakersb.repository.CurrencyRepository;
import com.exavalu.budgetbakersb.repository.LabelRepository;
import com.exavalu.budgetbakersb.repository.PaymentStatusRepository;
import com.exavalu.budgetbakersb.repository.PaymentTypeRepository;
import com.exavalu.budgetbakersb.repository.RecordRepository;
import com.exavalu.budgetbakersb.repository.RecordTypeRepository;
import com.exavalu.budgetbakersb.entity.RecordTypeEnum;
import com.exavalu.budgetbakersb.entity.CurrencyCodeEnum;
import com.exavalu.budgetbakersb.entity.PaymentStatusEnum;

//date parsing imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RecordServices {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	LabelRepository labelRepository;

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	@Autowired
	PaymentStatusRepository paymentStatusRepository;

	@Autowired
	AccountTypeRepository accountTypeRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	RecordTypeRepository recordTypeRepository;

	@Autowired
	RecordRepository recordRepository;

	public List<Category> getAllCategories() {

		return categoryRepository.findAll();
	}

	public List<Label> getAllLabels() {
		// TODO Auto-generated method stub
		return labelRepository.findAll();
	}

	public List<PaymentType> getAllPaymentTypes() {
		return paymentTypeRepository.findAll();
	}

	public List<PaymentStatus> getAllPaymentStatus() {

		return paymentStatusRepository.findAll();
	}

	public List<Currency> getAllCurrencies() {
		// TODO Auto-generated method stub
		return currencyRepository.findAll();
	}

	public List<AccountType> getAllAccountType() {

		return accountTypeRepository.findAll();
	}

	public List<Account> getAllAccountOfUser(int userId) {

		return accountRepository.findByUserId(userId);
	}

	public List<RecordType> getAllRecordTypeList() {
		
		return recordTypeRepository.findAll();
		
	}
	
	
	public void saveRecord(Record record) {

		recordRepository.save(record);

	}

	public void processUploadedFile(MultipartFile file, int user_id) {
		List<Record> recordList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			boolean isHeaderRow = true;
			String line;

			while ((line = reader.readLine()) != null) {
				if (isHeaderRow) {
					isHeaderRow = false;
					continue;
				}

				String[] values = line.split(",");
				Record record = createRecordFromValues(values, user_id);

				recordList.add(record);
//                Record record = new Record();
			}

			recordRepository.saveAll(recordList);
			// Handle the result as needed
		} catch (IOException e) {
			// Handle the exception
		}
	}

	private Record createRecordFromValues(String[] values, int user_id) {
		Record record = new Record();
		record.setUserId(user_id);
//	    System.out.println(recordTypeRepository.findIdByRecordTypeName(RecordTypeEnum.valueOf(values[0])));
		record.setRecordTypeId(recordTypeRepository.findIdByRecordTypeName(RecordTypeEnum.valueOf(values[0])));
		record.setAmount(Double.parseDouble(values[1]));
		record.setCurrencyId(currencyRepository.findIdByCurrencyName(CurrencyCodeEnum.valueOf(values[2])));
		System.out.println(record.getRecordTypeId());
		record.setAccountId(accountRepository.findIdByAccountName(values[3]));
		record.setCategoryId(categoryRepository.findIdByCategoryName(values[4]));
		record.setLabelId(labelRepository.findIdByLabelName(values[5]));
		record.setDate(values[6]);
		record.setTime(values[7]);
		record.setPayer(values[8]);
		record.setNote(values[9]);
		record.setPaymentTypeId(paymentTypeRepository.findIdByPaymentTypeName(values[10]));
		record.setPaymentStatusId(
				paymentStatusRepository.findIdByPaymentStatusName(PaymentStatusEnum.valueOf(values[11])));
		record.setPlace(values[12]);
		System.out.println(values[13]);
		record.setToAccountId(accountRepository.findIdByAccountName(values[13]));
		record.setToAmount(Double.parseDouble(values[14]));
		record.setToCurrencyId(currencyRepository.findIdByCurrencyName(CurrencyCodeEnum.valueOf(values[15])));

		System.out.println(record.toString());
		System.out.println("record type id is " + record.getRecordTypeId());
		return record;
	}

	public List<Record> findByAccountId(int id) {
		return recordRepository.findByAccountId(id);
	}

	public List<Record> getAllRecordsByUserId(int userId) {

		return recordRepository.findByUserId(userId);
	}

	public Record getRecordByRecordId(int recordId) {

		return recordRepository.getReferenceById(recordId);
	}

	public void deleteRecordById(int recordId) {
		recordRepository.deleteById(recordId);

	}


	
	
	
	
/*
	
	public List<Record> getFilteredData(int userId, List<Integer> accountCheckedList, List<Integer> categoryCheckedList,
			List<Integer> currencyCheckedList, List<Integer> paymentTypeCheckedList,
			List<Integer> paymentStatusCheckedList, List<Integer> listCheckedList) {
		
		
		
		if(accountCheckedList.isEmpty())  {System.out.println("account cheeck list is empty***********************");}
		else {
			System.out.println("account cheeck list is not empty***********************");
		}
//		
//		if(accountCheckedList.size()==0) 
//		{
//			System.out.println("accountCheckedList is null*****************");
////			accountCheckedList = new ArrayList<Integer>();
//		}
//		else {
//			System.out.println("accountCheckedList is not null*****************");
//			System.out.println(accountCheckedList);
//		}
//		if(categoryCheckedList==null) 
//		{
//			categoryCheckedList = new ArrayList<Integer>();
//		}
//		if(currencyCheckedList==null) 
//		{
//			currencyCheckedList = new ArrayList<Integer>();
//		}
//		if(paymentTypeCheckedList==null) 
//		{
//			paymentTypeCheckedList = new ArrayList<Integer>();
//		}
//		if(paymentStatusCheckedList==null) 
//		{
//			paymentStatusCheckedList = new ArrayList<Integer>();
//		}
//		if(listCheckedList==null) 
//		{
//			listCheckedList = new ArrayList<Integer>();
//		}

//		case for all records empty
//		if()

		/*
		 * return recordRepository.getFilteredData(accountCheckedList,
		 * categoryCheckedList, currencyCheckedList, paymentTypeCheckedList,
		 * paymentStatusCheckedList, listCheckedList);
		 * 
		 * 
		 */

//		return recordRepository.findRecordsByAccountIdsAndCurrencyIds(accountCheckedList,currencyCheckedList);

		/*
		 * HashMap<Integer, String> listHashMap = new HashMap<>();
		 * 
		 * listHashMap.put(accountCheckedList.size(), "AccountId");
		 * listHashMap.put(categoryCheckedList.size(), "CategoryId");
		 * listHashMap.put(currencyCheckedList.size(), "CurrencyId");
		 * listHashMap.put(paymentTypeCheckedList.size(), "PaymentStatusId");
		 * listHashMap.put(paymentStatusCheckedList.size(), "PaymentTypeId");
		 * listHashMap.put(listCheckedList.size(), "LabelId");
		 * 
		 * 
		 * 
		 * // Find the number of entries and store corresponding values int entryCount =
		 * 0; ArrayList<String> correspondingValues = new ArrayList<>(); for
		 * (Map.Entry<Integer, String> entry : listHashMap.entrySet()) { if
		 * (entry.getKey() > 0) { entryCount++;
		 * correspondingValues.add(entry.getValue()); } }
		 * 
		 * 
		 * if(entryCount==0) { return recordRepository.findByUserId(userId); }
		 * 
		 * if(entryCount ==1) {
		 * 
		 * String param_1=correspondingValues.get(0)+"In";
		 * 
		 * String base_query = "findByUserId";
		 * 
		 * String final_query =
		 * base_query+param_1+"(accountCheckedList,categoryCheckedList)";
		 * 
		 * 
		 * 
		 * return recordRepository.final_query;
		 * 
		 * }
		 * 
		 */

	
/*
//		all list empty
		if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserId(userId);
		}

//		one list has value
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndAccountIdIn(userId, accountCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCurrencyIdIn(userId, currencyCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCategoryIdIn(userId, categoryCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndPaymentStatusIdIn(userId, paymentStatusCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndPaymentTypeIdIn(userId, paymentTypeCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndLabelIdIn(userId, listCheckedList);
		}
		
*/

// two list has value

//		else if(accountCheckedList.size()>0 && 
//				currencyCheckedList.size()>0 && 
//				categoryCheckedList.size()==0 && 
//				paymentStatusCheckedList.size() ==0 && 
//				paymentTypeCheckedList.size()==0 && 
//				listCheckedList.size()==0)
//		{
//			return recordRepository.findByUserIdAndLabelIdIn(userId,listCheckedList);
//		}
//		

		
/*
		// Two lists have values
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndAccountIdInAndCurrencyIdIn(userId, accountCheckedList,
					currencyCheckedList);
		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndAccountIdInAndCategoryIdIn(userId, accountCheckedList,
					categoryCheckedList);
		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndAccountIdInAndPaymentStatusIdIn(userId, accountCheckedList,
					paymentStatusCheckedList);
		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndAccountIdInAndPaymentTypeIdIn(userId, accountCheckedList,
					paymentTypeCheckedList);

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndAccountIdInAndLabelIdIn(userId, accountCheckedList, listCheckedList);

		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCurrencyIdInAndCategoryIdIn(userId, currencyCheckedList,
					categoryCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCurrencyIdInAndPaymentStatusIdIn(userId, currencyCheckedList,
					paymentStatusCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCurrencyIdInAndPaymentTypeIdIn(userId, currencyCheckedList,
					paymentTypeCheckedList);

		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndCurrencyIdInAndLabelIdIn(userId, currencyCheckedList,
					listCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {

			return recordRepository.findByUserIdAndCategoryIdInAndPaymentStatusIdIn(userId, categoryCheckedList,
					paymentStatusCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndCategoryIdInAndPaymentTypeIdIn(userId, categoryCheckedList,
					paymentTypeCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndCategoryIdInAndLabelIdIn(userId, categoryCheckedList,
					listCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {
			return recordRepository.findByUserIdAndPaymentStatusIdInAndPaymentTypeIdIn(userId, paymentStatusCheckedList,
					paymentTypeCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndPaymentStatusIdInAndLabelIdIn(userId, paymentStatusCheckedList,
					listCheckedList);
		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() > 0) {
			return recordRepository.findByUserIdAndPaymentTypeIdInAndLabelIdIn(userId, paymentTypeCheckedList,
					listCheckedList);
		}

		// Three lists have values
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {

		}

		// Remaining combinations for three lists go here

		// Four lists have values
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() == 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {

		}

		// Remaining combinations for four lists go here

		// Five lists have values
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() == 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() == 0
				&& listCheckedList.size() > 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() == 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() > 0) {

		}

		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() == 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() > 0) {

		}

		else if (accountCheckedList.size() == 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() > 0) {

		}

		// Remaining combinations for five lists go here

		// All six lists have values
		else if (accountCheckedList.size() > 0 && currencyCheckedList.size() > 0 && categoryCheckedList.size() > 0
				&& paymentStatusCheckedList.size() > 0 && paymentTypeCheckedList.size() > 0
				&& listCheckedList.size() > 0) {

		}

		return null;
	}
	
*/
	
/*	

	public List<Record> getRecordAccordingToFilter(List<Record> allRecord, int userId, List<Integer> accountCheckedList,
			List<Integer> categoryCheckedList, List<Integer> currencyCheckedList, List<Integer> paymentTypeCheckedList,
			List<Integer> paymentStatusCheckedList, List<Integer> listCheckedList) {

		return null;
	}

	
*/	

	
	
/*	
	public List<Record> getRecordAccordingToFilter2(List<Record> allRecord, int userId,
			List<Integer> accountCheckedList, List<Integer> currencyCheckedList) {
		List<Record> filteredRecords = new ArrayList<>();

		if ((accountCheckedList == null || accountCheckedList.isEmpty())
				&& (currencyCheckedList == null || currencyCheckedList.isEmpty())) {
// If both lists are empty, return all records
			return allRecord;
		}

		if (accountCheckedList == null || accountCheckedList.isEmpty()) {
// If accountCheckedList is empty, filter records based on currencyCheckedList
			for (Record record : allRecord) {
				if (currencyCheckedList.contains(record.getCurrencyId())) {
					filteredRecords.add(record);
				}
			}
		} else if (currencyCheckedList == null || currencyCheckedList.isEmpty()) {
// If currencyCheckedList is empty, filter records based on accountCheckedList
			for (Record record : allRecord) {
				if (accountCheckedList.contains(record.getAccountId())) {
					filteredRecords.add(record);
				}
			}
		} else {
// Filter records based on both accountCheckedList and currencyCheckedList
			for (Record record : allRecord) {
				if (accountCheckedList.contains(record.getAccountId())
						&& currencyCheckedList.contains(record.getCurrencyId())) {
					filteredRecords.add(record);
				}
			}
		}

		return filteredRecords;
	}

	*/
	
	
	
	
	
	/************************** filter record services goes here   ***************************/
	
	public List<Record> filterByAccountId(List<Record> allRecordBasedOnDate,List<Integer> accountCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(accountCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (accountCheckedList.contains(record.getAccountId())) {
					filteredRecords.add(record);
				}
			}
			
		}
		
		
		
		return filteredRecords;
		
	}
	
	

	public List<Record> filterByCategoryId(List<Record> allRecordBasedOnDate,List<Integer> categoryCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(categoryCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (categoryCheckedList.contains(record.getCategoryId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	
	public List<Record> filterByCurrencyId(List<Record> allRecordBasedOnDate,List<Integer> currencyCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(currencyCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (currencyCheckedList.contains(record.getCurrencyId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	
	public List<Record> filterByPaymentTypeId(List<Record> allRecordBasedOnDate,List<Integer> paymentTypeCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(paymentTypeCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (paymentTypeCheckedList.contains(record.getPaymentTypeId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	public List<Record> filterByPaymentStatusId(List<Record> allRecordBasedOnDate,List<Integer> paymentStatusCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(paymentStatusCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (paymentStatusCheckedList.contains(record.getPaymentStatusId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	
	public List<Record> filterByLabelId(List<Record> allRecordBasedOnDate,List<Integer> listCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(listCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (listCheckedList.contains(record.getLabelId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	public List<Record> filterByRecordTypeId(List<Record> allRecordBasedOnDate,List<Integer> recordTypeCheckedList)
	{
		List<Record> filteredRecords = new ArrayList<>();
		
//		if accountCheckedList is empty then return all
		if(recordTypeCheckedList.isEmpty())
		{
			return allRecordBasedOnDate;
		}
		else {
			
			for (Record record : allRecordBasedOnDate) {
				if (recordTypeCheckedList.contains(record.getRecordTypeId())) {
					filteredRecords.add(record);
				}
			}
			
		}		
		return filteredRecords;
		
	}
	
	
	
	public List<Record> filterByDate(List<Record> allRecordBasedOnDate,String startDateStr,String endDateStr)
	{
		if(startDateStr=="" || endDateStr=="")
		{
			return allRecordBasedOnDate;
		}
		
		List<Record> filteredRecords = new ArrayList<>();
        
        // Parse start and end dates
        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        
        // Filter records based on date range
        for (Record record : allRecordBasedOnDate) {
            Date recordDate = parseDate(record.getDate());
            if (recordDate != null && ( recordDate.equals(startDate) || recordDate.equals(endDate) ||(recordDate.after(startDate) && recordDate.before(endDate)) )){
                filteredRecords.add(record);
            }
        }
        
        return filteredRecords;
		
		
		
		
	}
	
	
//	date parsing function
	
    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return null;
        }
    }
	
	
	public List<Record> filterRecordAccordingSidebar(List<Record> allRecordBasedOnDate, List<Integer> accountCheckedList, List<Integer> categoryCheckedList,
			List<Integer> currencyCheckedList, List<Integer> paymentTypeCheckedList,
			List<Integer> paymentStatusCheckedList, List<Integer> labelCheckedList,List<Integer> recordTypeCheckedList,List<Integer> monthCheckedList,String startingDate,String endingDate)
	{
		
		
//		all the checkbox are empty then return all
//		if(	accountCheckedList.isEmpty() && 
//				categoryCheckedList.isEmpty() && 
//				currencyCheckedList.isEmpty() && 
//				paymentTypeCheckedList.isEmpty() && 
//				paymentStatusCheckedList.isEmpty() && 
//				labelCheckedList.isEmpty() 
//				)
//		{
//			return allRecordBasedOnDate;
//		}
		
		
		
		List<Record> filterByAccountId = filterByAccountId(allRecordBasedOnDate, accountCheckedList);
		
		List<Record> filterByCategoryId = filterByCategoryId(filterByAccountId, categoryCheckedList);
		
		List<Record> filterByCurrencyId = filterByCurrencyId(filterByCategoryId, currencyCheckedList);
		
		
		List<Record> filterByPaymentStatusId = filterByPaymentStatusId(filterByCurrencyId, paymentStatusCheckedList);
		
		List<Record> filterByPaymentTypeId = filterByPaymentTypeId(filterByPaymentStatusId, paymentTypeCheckedList);
		
		
		List<Record> filterByRecordTypeId = filterByRecordTypeId(filterByPaymentTypeId,recordTypeCheckedList);
		
		List<Record> filterByLabelId = filterByLabelId(filterByRecordTypeId, labelCheckedList);
		
		List<Record> filterByDate = filterByDate(filterByLabelId, startingDate, endingDate);
		
		
		
		return filterByDate;
	}


	

	

//	    public List<Record> filterRecords(List<Record> allRecords, String startDateStr, String endDateStr) {
//	        List<Record> filteredRecords = new ArrayList<>();
//	        
//	        // Parse start and end dates
//	        Date startDate = parseDate(startDateStr);
//	        Date endDate = parseDate(endDateStr);
//	        
//	        // Filter records based on date range
//	        for (Record record : allRecords) {
//	            Date recordDate = parseDate(record.getDate());
//	            if (recordDate != null && recordDate.after(startDate) && recordDate.before(endDate)) {
//	                filteredRecords.add(record);
//	            }
//	        }
//	        
//	        return filteredRecords;
//	    }
	    

	

	
	
	

}
