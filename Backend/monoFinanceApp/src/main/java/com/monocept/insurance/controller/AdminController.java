package com.monocept.insurance.controller;

import java.util.List;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.AddSchemeDto;
import com.monocept.insurance.dto.AdminGetDto;
import com.monocept.insurance.dto.AdminPostDto;
import com.monocept.insurance.dto.ClaimApproveDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PlanDto;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.InsurancePolicy;
import com.monocept.insurance.repository.InsurancePlanRepository;
import com.monocept.insurance.repository.InsuranceSchemeRepository;
import com.monocept.insurance.service.AdminService;
import com.monocept.insurance.service.InsurancePlanService;
import com.monocept.insurance.service.InsuranceSchemeService;

@RequestMapping("/insuranceapp")
@RestController
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private InsurancePlanService insurancePlanService;
	
	@PostMapping("/admin")
	public  ResponseEntity<AdminGetDto> addAdmin(@RequestBody AdminPostDto adminPostDto)
	{
		System.out.println("admin dto "+adminPostDto);
		AdminGetDto admin = adminService.addAdmin(adminPostDto);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<Page<AdminGetDto>>getAllAdmin(@RequestParam int pageNumber,@RequestParam int pageSize)
	{
		System.out.println("pagenumber,pagesize are "+pageNumber+" ,"+pageSize);
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<AdminGetDto> admins = adminService.getAllAdmin(pageable);
		
		
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/agentClaims")
	public ResponseEntity<List<Agent>>agentClaims(){
		
		List<Agent> agents=adminService.getAgentClaims();
		
		return ResponseEntity.ok(agents);
		
	}
	
	@GetMapping("/policyClaims")
	public ResponseEntity<List<InsurancePolicy>>policyClaims(){
		
		List<InsurancePolicy> agents=adminService.getpolicyClaims();
		
		return ResponseEntity.ok(agents);
		
	}
	
	
	@PostMapping("/agentClaimApprove")
	public ResponseEntity<Message>agentClaimApprove(@RequestBody ClaimApproveDto claimAppproveDto){
		
		System.out.println(claimAppproveDto);
		
		Message message=adminService.agentClaimApprove(claimAppproveDto);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@GetMapping("/policyClaimApprove")
	public ResponseEntity<Message>policyClaimApprove(@RequestParam long policyId ){
		
		Message message=adminService.agentPolicyClaimApprove(policyId);
		
		return ResponseEntity.ok(message);
		
	}
	
	@PostMapping("/agentClaimReject")
	public ResponseEntity<Message>agentClaimReject(@RequestBody ClaimApproveDto claimAppproveDto){
		
		System.out.println(claimAppproveDto);
		
		Message message=adminService.agentClaimReject(claimAppproveDto);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@GetMapping("/policyClaimReject")
	public ResponseEntity<Message>policyClaimReject(@RequestParam long policyId ){
		
		Message message=adminService.agentPolicyClaimReject(policyId);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	
	
	

}
