package com.dinesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.binding.ChildRequest;
import com.dinesh.binding.DcSummary;
import com.dinesh.service.DcService;

@RestController
public class ChildRestController {
	@Autowired
	private DcService service;

	@PostMapping("/childrens")
	public ResponseEntity<DcSummary> saveChilds(@RequestBody ChildRequest request) {
		Long caseNum = service.saveChildrans(request);

		DcSummary summery = service.getSummery(caseNum);
		return new ResponseEntity<>(summery, HttpStatus.OK);
	}
}
