package com.ristic.csvutils.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ristic.csvutils.domain.CsvRecord;
import com.ristic.csvutils.repository.CsvRecordsRepository;
import com.ristic.csvutils.service.CsvUtilsService;

@Service
public class CsvUtilsServiceImpl implements CsvUtilsService {

	private final static String COMMA_DELIMITER = ",";
	
	@Autowired
	CsvRecordsRepository repository;

	@Override
	public void upload(MultipartFile multipartFile) {
		List<List<String>> records = readCsvFile(multipartFile);
		records.remove(0);
		List<CsvRecord> csvRecords = new ArrayList<>();
		records.forEach(record -> csvRecords.add(convertToCsvRecord(record)));
		repository.saveAll(csvRecords);
	}

	private CsvRecord convertToCsvRecord(List<String> record) {
		CsvRecord csvRecord = new CsvRecord();
		csvRecord.setSource(record.get(0));
		csvRecord.setCodeListCode(record.get(1));
		csvRecord.setCode(record.get(2));
		csvRecord.setDisplayValue(record.get(3));
		csvRecord.setLongDescription(record.get(4));
		// TODO: Issue with empty fields.
		// if (!StringUtils.isEmpty(record.get(5))) {
		// 	csvRecord.setFromDate(LocalDate.parse(record.get(5), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		// }
		// if (!StringUtils.isEmpty(record.get(6))) {
		// 	csvRecord.setToDate(LocalDate.parse(record.get(6), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		// }
		// csvRecord.setSortingPriority(Integer.valueOf(record.get(7)));
		return csvRecord;
	}

	/**
	 * Read CSV file into list of records (reference from baeldung.com).
	 * 
	 * @param multipartFile
	 * @return list of records
	 */
	private List<List<String>> readCsvFile(MultipartFile multipartFile) {
		List<List<String>> records = new ArrayList<>();
		File file = new File(System.getProperty("java.io.tmpdir")+"/file.tmp");
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				records.add(getRecordFromLine(scanner.nextLine()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return records;
	}

	/**
	 * Scanning single line (reference from baeldung.com).
	 * 
	 * @param line
	 * @return content of a single line
	 */
	private List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}

	@Override
	public List<CsvRecord> getRecords() {
		return repository.findAll();
	}

	@Override
	public CsvRecord getRecord(String code) {
		return repository.findByCode(code);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();		
	}

}
