package com.dinesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.binding.PlanSelection;
import com.dinesh.service.DcService;

@RestController
public class PlanSelectionRestController {
	@Autowired
	private DcService service;

	@PostMapping("/plansel")
	public ResponseEntity<Long> planSelection(@RequestBody PlanSelection planSel) {
		Long caseNum = service.savePlanSelection(planSel);
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
}
