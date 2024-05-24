package com.exavalu.budgetbakersb.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exavalu.budgetbakersb.repository.RecordRepository;
import com.exavalu.budgetbakersb.entity.Record;

@Service
public class RecordService3 {

	@Autowired
	RecordRepository recordRepository;

	public List<Double> getCumulativeExpense(int userid, String accountId) {
		// Convert the comma-separated string of accountIds to a list of integers
		List<Integer> accountIdList = Arrays.stream(accountId.split(",")).map(String::trim) // Trim each token to remove
																							// spaces
				.map(Integer::parseInt).collect(Collectors.toList());

		// Call the repository method with the updated parameters
		List<Object[]> result = recordRepository.findCumulativeExpenseByUserIdAndAccountId(userid, accountIdList);
		List<Double> cumulativeExpense = new ArrayList<>();
		for (Object[] row : result) {
			double cumulativeExpenses = (Double) row[2];
			cumulativeExpense.add(cumulativeExpenses);
		}
		return cumulativeExpense;
	}

	public List<Record> getExpenseList(int userid, String accountId) {
		List<Integer> accountIdList = Arrays.stream(accountId.split(",")).map(String::trim)

				.map(Integer::parseInt).collect(Collectors.toList());

		List<Object[]> result = recordRepository.findExpenseListByUserIdAndAccountId(userid, accountIdList);
		List<Record> expenseList = new ArrayList<>();

		for (Object[] row : result) {
			Record record = new Record();
			record.setAmount((Double) row[1]);
			record.setDate((String) row[0]);
			expenseList.add(record);
		}
		return expenseList;
	}

	public List<Record> getIncomeList(int userid, String accountId) {
		List<Integer> accountIdList = Arrays.stream(accountId.split(",")).map(String::trim)

				.map(Integer::parseInt).collect(Collectors.toList());

		List<Object[]> result = recordRepository.findIncomeListByUserIdAndAccountId(userid, accountIdList);
		List<Record> incomeList = new ArrayList<>();

		for (Object[] row : result) {
			Record record = new Record();
			record.setAmount((Double) row[1]);
			record.setDate((String) row[0]);
			incomeList.add(record);
		}
		return incomeList;
	}

	public List<Record> getdateList(int userid) {
		// TODO Auto-generated method stub
		List<Object[]> result = recordRepository.findDateListByUserId(userid);
		List<Record> dateList = new ArrayList<>();

		for (Object[] row : result) {
			Record record = new Record();
			record.setDate((String) row[0]);
			dateList.add(record);
		}
		return dateList;
	}

	public List<Record> getExpenseListCumulativeCashFlow(int userid, String accountId) {
		List<Integer> accountIdList = Arrays.stream(accountId.split(",")).map(String::trim)

				.map(Integer::parseInt).collect(Collectors.toList());

		List<Object[]> result = recordRepository.findExpenseListCumulativeCashFlowByUserIdAndAccountId(userid,
				accountIdList);
		List<Record> expenseListCumulativeCashFlow = new ArrayList<>();

		for (Object[] row : result) {
			Record record = new Record();
			record.setAmount((Double) row[1]);
			record.setDate((String) row[0]);
			expenseListCumulativeCashFlow.add(record);
		}
		return expenseListCumulativeCashFlow;
	}

	public List<Record> getIncomeListCumulativeCashFlow(int userid, String accountId) {
		List<Integer> accountIdList = Arrays.stream(accountId.split(",")).map(String::trim)

				.map(Integer::parseInt).collect(Collectors.toList());

		List<Object[]> result = recordRepository.findIncomeListCumulativeCashFlowByUserIdAndAccountId(userid,
				accountIdList);
		List<Record> incomeListCumulativeCashFlow = new ArrayList<>();

		for (Object[] row : result) {
			Record record = new Record();
			record.setAmount((Double) row[1]);
			record.setDate((String) row[0]);
			incomeListCumulativeCashFlow.add(record);
		}
		return incomeListCumulativeCashFlow;
	}

}
