package com.monocept.insurance.service;

import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PlanDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.EditPlanDto;

public interface InsurancePlanService {
	
	Message addPlan (PlanDto planDto);
	Message activePlan (Long planId);
	Message inActivePlan (Long PlanId);
	Message editPlan (EditPlanDto editPlanDto);
	Page<PlanDto> getAllPlans(Pageable pageable);
	Page<PlanDto> ActivePlans(Pageable pageable);

}
