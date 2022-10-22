package com.dinesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.binding.IncomeDtls;
import com.dinesh.service.DcService;

@RestController
public class IncomeRestController {
	@Autowired
	private DcService service;

	@PostMapping("/income")
	public ResponseEntity<Long> saveIncome(@RequestBody IncomeDtls income) {
		Long caseNum = service.saveIncomeData(income);
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
}
