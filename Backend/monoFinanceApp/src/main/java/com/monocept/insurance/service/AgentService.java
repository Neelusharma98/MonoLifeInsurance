package com.monocept.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.AgentClaimDto;
import com.monocept.insurance.dto.AgentDto;
import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.Agent;

public interface AgentService {
	
	JwtAuthResponse agentLogin(LoginDto logindto);
	AgentGetDto getAgentByUsername(String username);
	Message addAgent(AgentDto agentDto);
	Page<AgentGetDto> getAllAgents(Pageable pageable);
	Message editAgent(EditProfileDto editProfileDto);
	Message inActiveAgent(long id);
	Message ActiveAgent(long id);
	Agent getAgentDetail(String username);
	Message makeClaim(AgentClaimDto agentClaimDto);
	Page<AccountDto> getAllAccounts(Pageable pageable, long id);
}
