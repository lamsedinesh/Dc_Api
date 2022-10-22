package com.dinesh.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinesh.binding.ChildDtls;
import com.dinesh.binding.ChildRequest;
import com.dinesh.binding.CitizenApp;
import com.dinesh.binding.DcSummary;
import com.dinesh.binding.EducationDtls;
import com.dinesh.binding.IncomeDtls;
import com.dinesh.binding.PlanSelection;
import com.dinesh.entity.CitizenAppEntity;
import com.dinesh.entity.DcCaseEntity;
import com.dinesh.entity.DcChildrenEntity;
import com.dinesh.entity.DcEducationEntity;
import com.dinesh.entity.DcIncomeEntity;
import com.dinesh.entity.PlanEntity;
import com.dinesh.repository.CitizenAppRepository;
import com.dinesh.repository.DcCaseRepo;
import com.dinesh.repository.DcChildrenRepo;
import com.dinesh.repository.DcEducationRepo;
import com.dinesh.repository.DcIncomeRepo;
import com.dinesh.repository.PlanRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class DcServiceImpl implements DcService {
	private static final Object ChildDtls = null;

	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepository planRepo;

	@Autowired
	private DcIncomeRepo incomeRepo;

	@Autowired
	private DcEducationRepo eduRepo;

	@Autowired
	private DcChildrenRepo childRepo;

	@Autowired
	private CitizenAppRepository appRepo;

	@Override
	public Long loadCaseNum(Integer appId) {

		Optional<CitizenAppEntity> app = appRepo.findById(appId);
		if (app.isPresent()) {
			DcCaseEntity entity = new DcCaseEntity();
			entity.setAppId(appId);

			entity = dcCaseRepo.save(entity);
			return entity.getCaseNum();
		}
		return 0l;
	}

	@Override
	public Map<Integer, String> getPlanName() {
		List<PlanEntity> findAll = planRepo.findAll();

	Map<Integer,String> plansMap = new HashMap<>();

		for (PlanEntity entity : findAll) {
			plansMap.put(entity.getPlanId(), entity.getPlanName());
		}
		return plansMap;
	}

	@Override
	public Long savePlanSelection(PlanSelection planSelection) {

		Optional<DcCaseEntity> findById = dcCaseRepo.findById(planSelection.getCaseNum());
		if (findById.isPresent()) {
			DcCaseEntity dcCaseEntity = findById.get();
			dcCaseEntity.setPlanId(planSelection.getPlanId());

			dcCaseRepo.save(dcCaseEntity);
			return planSelection.getCaseNum();

		}
		return null;
	}

	@Override
	public Long saveIncomeData(IncomeDtls income) {
		DcIncomeEntity entity = new DcIncomeEntity();
		BeanUtils.copyProperties(income, entity);
		incomeRepo.save(entity);
		return income.getCaseNum();
	}

	@Override
	public Long saveEducation(EducationDtls education) {
		// convert binding data into education data
		DcEducationEntity entity = new DcEducationEntity();
		BeanUtils.copyProperties(education, entity);
		eduRepo.save(entity);

		return education.getCaseNum();
	}

	@Override
	public Long saveChildrans(ChildRequest request) {
		List<ChildDtls> childs = request.getChilds();
		for (ChildDtls ch : childs) {
			DcChildrenEntity entity = new DcChildrenEntity();
			BeanUtils.copyProperties(ch, childs);
			childRepo.save(entity); // save one by one child
		}
		//childRepo.saveAll(entities); //save all child at a time
		return request.getCaseNum();
	}

	@Override
	public DcSummary getSummery(Long caseNumber) {
		String planName = "";
		
		//get the data from database
		DcIncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNumber);
		DcEducationEntity educationEntity = eduRepo.findByCaseNum(caseNumber);
		List<DcChildrenEntity> childEntity = childRepo.findByCaseNum(caseNumber);
		Optional<DcCaseEntity> dcCase = dcCaseRepo.findById(caseNumber);
		if (dcCase.isPresent()) {
			Integer planId = dcCase.get().getPlanId();
			Optional<PlanEntity> plan = planRepo.findById(planId);
			if (plan.isPresent()) {
				planName = plan.get().getPlanName();
			}
		}

		// set the data to summery object
		DcSummary summary = new DcSummary();
		summary.setPlanName(planName);

		IncomeDtls income = new IncomeDtls();
		BeanUtils.copyProperties(incomeEntity, income);
		summary.setIncome(income);

		EducationDtls edu = new EducationDtls();
		BeanUtils.copyProperties(educationEntity, edu);
		summary.setEducation(edu);

		List<ChildDtls> childs = new ArrayList();
		for (DcChildrenEntity entity : childEntity) {
			ChildDtls ch = new ChildDtls();
			BeanUtils.copyProperties(edu, ch);
			childs.add(ch);

		}
		summary.setChilds(childs);

		return summary;
	}

}
