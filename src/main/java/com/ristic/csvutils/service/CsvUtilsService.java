package com.ristic.csvutils.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvUtilsService {

	/**
	 * Uploading CSV file and storing into database.
	 * 
	 * @param file
	 * @return
	 */
	void upload(MultipartFile file);

}
