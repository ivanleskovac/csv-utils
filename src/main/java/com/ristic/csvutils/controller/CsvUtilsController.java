package com.ristic.csvutils.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ristic.csvutils.service.CsvUtilsService;

@RestController
@RequestMapping("/csv")
public class CsvUtilsController {

	@Autowired
	CsvUtilsService csvUtilsService;

	@PostMapping("/upload")
	public ResponseEntity<Object> uploadCsvFile(@RequestParam("file") MultipartFile file) {
		csvUtilsService.upload(file);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
