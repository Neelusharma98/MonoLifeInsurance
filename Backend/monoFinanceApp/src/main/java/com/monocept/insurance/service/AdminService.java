package com.monocept.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.AdminGetDto;
import com.monocept.insurance.dto.AdminPostDto;
import com.monocept.insurance.dto.ClaimApproveDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.InsurancePolicy;

public interface AdminService {
	
	
	AdminGetDto addAdmin(AdminPostDto adminPostDto);
	Page<AdminGetDto> getAllAdmin(Pageable pageable);
	public JwtAuthResponse adminLogin(LoginDto loginDto);
	String validateuser(String token);
	List<Agent> getAgentClaims();
	List<InsurancePolicy> getpolicyClaims();
	Message agentPolicyClaimApprove(long policyId);
	Message agentClaimApprove(ClaimApproveDto claimAppproveDto);
	Message agentClaimReject(ClaimApproveDto claimAppproveDto);
	Message agentPolicyClaimReject(long policyId);

}
