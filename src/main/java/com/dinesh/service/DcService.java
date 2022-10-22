package com.dinesh.service;

import java.util.List;
import java.util.Map;

import com.dinesh.binding.ChildDtls;
import com.dinesh.binding.ChildRequest;
import com.dinesh.binding.CitizenApp;
import com.dinesh.binding.DcSummary;
import com.dinesh.binding.EducationDtls;
import com.dinesh.binding.IncomeDtls;
import com.dinesh.binding.PlanSelection;

public interface DcService {

	public Long loadCaseNum(Integer appId);

	public Map<Integer,String> getPlanName();

	public Long savePlanSelection(PlanSelection planSelection);

	public Long saveIncomeData(IncomeDtls income);

	public Long saveEducation(EducationDtls education);

	public Long saveChildrans(ChildRequest request); // represent casenum and multiple child

	public DcSummary getSummery(Long caseNumber);

}
