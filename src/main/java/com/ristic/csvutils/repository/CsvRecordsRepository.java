package com.ristic.csvutils.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ristic.csvutils.domain.CsvRecord;

public interface CsvRecordsRepository extends JpaRepository<CsvRecord, Long> {

	CsvRecord findByCode(String code);

}
