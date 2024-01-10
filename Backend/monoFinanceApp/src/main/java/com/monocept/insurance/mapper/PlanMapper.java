package com.monocept.insurance.mapper;

import com.monocept.insurance.dto.PlanDto;
import com.monocept.insurance.entity.InsurencePlan;

public class PlanMapper {

	public static PlanDto planToPlanDto(InsurencePlan plan) {
		PlanDto planDto = new PlanDto();
		planDto.setPlanId(plan.getPlanId());
		planDto.setPlanName(plan.getPlanName());
		planDto.setStatus(plan.isIsactive()==true?"Active":"Inactive");
		return planDto;
	}

}
