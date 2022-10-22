package com.dinesh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.binding.CreateCaseResponse;
import com.dinesh.entity.DcCaseEntity;
import com.dinesh.service.DcService;

@RestController
public class CreateCaseRestController {
	@Autowired
	private DcService service;

	@GetMapping("/case/{appId}")
	public ResponseEntity<CreateCaseResponse> createCase(@PathVariable Integer appId) {
		Long caseNum = service.loadCaseNum(appId);

		Map<Integer, String> planName = service.getPlanName();

		CreateCaseResponse response = new CreateCaseResponse();
		response.setCaseNum(caseNum);
		response.setPlanName(planName);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
