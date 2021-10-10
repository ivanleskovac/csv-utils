package com.ristic.csvutils.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ristic.csvutils.domain.CsvHeaderEnum;
import com.ristic.csvutils.domain.CsvRecord;
import com.ristic.csvutils.repository.CsvRecordsRepository;
import com.ristic.csvutils.service.CsvUtilsService;

@Service
public class CsvUtilsServiceImpl implements CsvUtilsService {

	@Autowired
	CsvRecordsRepository repository;

	@Override
	public void upload(MultipartFile multipartFile) {
		Iterable<CSVRecord> records = null;
		try {
			records = readCsvFile(multipartFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<CsvRecord> csvRecords = new ArrayList<>();
		records.forEach(record -> csvRecords.add(convertToCsvRecord(record)));
		repository.saveAll(csvRecords);
	}

	private Iterable<CSVRecord> readCsvFile(MultipartFile multipartFile) throws IOException {
		File file = convertMultipartFile(multipartFile);
		Reader reader = new FileReader(file);
		Builder builder = Builder.create().setHeader(CsvHeaderEnum.class);
		builder.setSkipHeaderRecord(true);
	    Iterable<CSVRecord> records = builder.build().parse(reader);
		return records;
	}
	
	private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir")+"/file-" + UUID.randomUUID() + ".tmp");
        multipartFile.transferTo(file);
        return file;
	}

	private CsvRecord convertToCsvRecord(CSVRecord record) {
		CsvRecord csvRecord = new CsvRecord();
		csvRecord.setSource(record.get(CsvHeaderEnum.source));
		csvRecord.setCodeListCode(record.get(CsvHeaderEnum.codeListCode));
		csvRecord.setCode(record.get(CsvHeaderEnum.code));
		csvRecord.setDisplayValue(record.get(CsvHeaderEnum.displayValue));
		csvRecord.setLongDescription(record.get(CsvHeaderEnum.longDescription));
		if (StringUtils.isNotEmpty(record.get(CsvHeaderEnum.fromDate))) {
			csvRecord.setFromDate(LocalDate.parse(record.get(CsvHeaderEnum.fromDate), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		}
		if (StringUtils.isNotEmpty(record.get(CsvHeaderEnum.toDate))) {
			csvRecord.setToDate(LocalDate.parse(record.get(CsvHeaderEnum.toDate), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		}
		if (NumberUtils.isParsable(record.get(CsvHeaderEnum.sortingPriority))) {
			csvRecord.setSortingPriority(Integer.valueOf(record.get(CsvHeaderEnum.sortingPriority)));
		}
		return csvRecord;
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
