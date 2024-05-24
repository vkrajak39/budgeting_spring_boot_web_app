package com.exavalu.budgetbakersb.services;
import com.exavalu.budgetbakersb.entity.Record;
import com.exavalu.budgetbakersb.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService2 {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordService2(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getRecordOfUserDateRange(int userId, String date1, String date2) {
        return recordRepository.getRecordOfUserDateRange(userId, date1, date2);
    }

    public List<Record> getRecordOfUserAccordingToAccountIdDateRange(int userId, int accountId, String date1, String date2) {
        return recordRepository.getRecordOfUserAccordingToAccountIdDateRange(userId, accountId, date1, date2);
    }
     
    public List<Object[]> getBalanceAtDateRange(int userId, String date1) {
        return recordRepository.getBalanceAtDateRange(userId, date1);
    }

    public List<Object[]> getAccountBalanceAtDateRange(int userId, int accountId, String date1) {
        return recordRepository.getAccountBalanceAtDateRange(userId, accountId, date1);
    }
}
