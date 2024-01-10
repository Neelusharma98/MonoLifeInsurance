package com.monocept.insurance.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PlanDto;
import com.monocept.insurance.dto.EditPlanDto;
import com.monocept.insurance.service.InsurancePlanService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/insuranceapp")
public class InsurancePlanController {


	@Autowired
	private InsurancePlanService insurancePlanService;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addPlan")

	public ResponseEntity<Message> addPlan(@RequestBody PlanDto addPlanDto)
	{   System.out.println("inside plan");
		Message plan = insurancePlanService.addPlan(addPlanDto);
		return new ResponseEntity<>(plan,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/plan")
	public ResponseEntity<Message> editPlan(@RequestBody EditPlanDto editPlanDto)
	{   System.out.println("inside plan");
		Message plan = insurancePlanService.editPlan(editPlanDto);
		return new ResponseEntity<>(plan,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/Plan")
	public ResponseEntity<Message> activePlan(@RequestParam Long planId)
	{   System.out.println("inside plan");
		Message plan = insurancePlanService.activePlan(planId);
		return new ResponseEntity<>(plan,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/Plan")
	public ResponseEntity<Message> inActivePlan(@RequestParam Long planId)
	{   System.out.println("inside plan");
		Message plan = insurancePlanService.inActivePlan(planId);
		return new ResponseEntity<>(plan,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allPlan")
	public ResponseEntity<Page<PlanDto>> getAllPlans(@RequestParam int pageNumber, @RequestParam int pageSize)
	{   System.out.println("inside plan");
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<PlanDto>plans = insurancePlanService.getAllPlans(pageable);
		HttpHeaders headers = new HttpHeaders();
	    headers.add("plans-count", String.valueOf(plans.getTotalElements()));
	    return ResponseEntity.ok().headers(headers).body(plans);
	}
	
	@GetMapping("/allPlans")
	public ResponseEntity<Page<PlanDto>> ActivePlans(@RequestParam int pageNumber, @RequestParam int pageSize)
	{   System.out.println("inside plan");
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<PlanDto>plans = insurancePlanService.ActivePlans(pageable);
		HttpHeaders headers = new HttpHeaders();
	    headers.add("plans-count", String.valueOf(plans.getTotalElements()));
	    return ResponseEntity.ok().headers(headers).body(plans);
	}
	
}
	
	


