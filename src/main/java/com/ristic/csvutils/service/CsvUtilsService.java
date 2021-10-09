package com.ristic.csvutils.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ristic.csvutils.domain.CsvRecord;

public interface CsvUtilsService {

	/**
	 * Uploading CSV file and storing into database.
	 * 
	 * @param file
	 * @return
	 */
	void upload(MultipartFile file);

	List<CsvRecord> getRecords();

}
