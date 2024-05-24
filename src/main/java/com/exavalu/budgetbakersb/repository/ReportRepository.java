package com.exavalu.budgetbakersb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exavalu.budgetbakersb.entity.Report;


@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {


}
