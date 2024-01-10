package com.monocept.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.GetPlanDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.EditPlanDto;
import com.monocept.insurance.dto.PlanDto;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.InsurencePlan;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.PlanMapper;
import com.monocept.insurance.repository.InsurancePlanRepository;

@Service
public class InsurancePlanServiceImpl implements InsurancePlanService {
	
	
	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Override
	public Message addPlan(PlanDto planDto) {
		System.out.println("plan name is---------------------"+planDto.getPlanName());
		List<InsurencePlan>list = insurancePlanRepository.findAll();
		for(InsurencePlan p:list)
		{
			if(p.getPlanName().equalsIgnoreCase(planDto.getPlanName()))
			{
				throw new InsuranceException("Plan Already Exists!");
				
			}
		}
		 
		InsurencePlan plan = new InsurencePlan();
		plan.setPlanName(planDto.getPlanName());
		System.out.println("plan name is"+plan.getPlanName());
		insurancePlanRepository.save(plan);
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("plan saved successfully!");
		return message;
	}

	@Override
	public Message activePlan(Long planId) {
		Optional<InsurencePlan> plan = insurancePlanRepository.findById(planId);
		if(!plan.isPresent())
		{
			throw new InsuranceException("Plan doesn't exists!");
		}
		
		if(plan.get().isIsactive())
		{
			throw new InsuranceException("Plan is already Active!");
		}
		InsurencePlan p = plan.get();
		p.setIsactive(true);
		insurancePlanRepository.save(p);
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("plan Activated successfully!");
		return message;
	}

	@Override
	public Message inActivePlan(Long planId) {
		Optional<InsurencePlan> plan = insurancePlanRepository.findById(planId);
		if(!plan.isPresent())
		{
			throw new InsuranceException("Plan doesn't exists!");
		}
		InsurencePlan p = plan.get();
		Message message = new Message();
		if(plan.get().isIsactive())
		{
			p.setIsactive(false);
			insurancePlanRepository.save(p);
			message.setStatus(HttpStatus.OK);
			message.setMessage("plan inActived successfully!");
			return message;
		}
		
		else
		{
		
		p.setIsactive(true);
		insurancePlanRepository.save(p);
		message.setStatus(HttpStatus.OK);
		message.setMessage("plan Actived successfully!");
		return message;
		}
	}

	@Override
	public Message editPlan(EditPlanDto editPlanDto) {
		Optional<InsurencePlan> plan = insurancePlanRepository.findById(editPlanDto.getPlanId());
		
		if(!plan.isPresent())
		{
			throw new InsuranceException("Plan doesn't exists!");
		}
		plan.get().setPlanName(editPlanDto.getPlanName()==null?plan.get().getPlanName():editPlanDto.getPlanName());
		insurancePlanRepository.save(plan.get());
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("plan updated successfully!");
		return message;
	}

	@Override
	public Page<PlanDto> getAllPlans(Pageable pageable) {
		Page<InsurencePlan> plans = insurancePlanRepository.findAll(pageable);
		Page<PlanDto>planList = plans.map(plan->(PlanMapper.planToPlanDto(plan)));
		
		if(planList.isEmpty())
		{
			throw new InsuranceException("No plan found!");
		}
		
		return planList;
	}

	@Override
	public Page<PlanDto> ActivePlans(Pageable pageable) {
		List<InsurencePlan>plans = insurancePlanRepository.findAll();
		List<PlanDto>plan = new ArrayList<>();
		for(InsurencePlan p:plans)
		{
			if(p.isIsactive())
			{
				plan.add(PlanMapper.planToPlanDto(p));
			}
				
		}

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), plan.size());
        Page<PlanDto> allPlans = new PageImpl<>(plan.subList(start, end), pageable, plan.size());
		return allPlans;
	}

}
