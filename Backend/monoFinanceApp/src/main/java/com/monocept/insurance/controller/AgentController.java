package com.monocept.insurance.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.AgentClaimDto;
import com.monocept.insurance.dto.AgentDto;
import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.service.AgentService;
import com.monocept.insurance.service.EmployeeService;


@RestController
@RequestMapping("/insuranceapp")
public class AgentController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private AgentService agentService;

	@PostMapping("/addagent")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Message> addAgent( @RequestBody AgentDto agentDto) {
        System.out.println("agent dto>>"+agentDto);
		Message message = agentService.addAgent(agentDto);
		

		return ResponseEntity.ok(message);

	}
	
	@GetMapping("/agent")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<AgentGetDto> getAgentByUsername( @RequestParam String username) {

		 AgentGetDto agentGetDto= agentService.getAgentByUsername(username);

		return ResponseEntity.ok(agentGetDto);
     }
	
	
	@GetMapping("/allAgents")
	//@PreAuthorize("hasRole('EMPLOYEE')")
	ResponseEntity<Page<AgentGetDto>>getAllAgents(@RequestParam int pageNumber, @RequestParam int pageSize) {
		
		 Pageable pageable=PageRequest.of(pageNumber, pageSize); 

		 Page<AgentGetDto>page= agentService.getAllAgents(pageable);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Agent-Count", String.valueOf(page.getTotalElements()));
		 return ResponseEntity.ok().headers(headers).body(page);	
	}
	
	@PutMapping("/agent")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<Message>editAgent(@RequestBody EditProfileDto agentGetDto ){
		 System.out.println("agent>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Message message= agentService.editAgent(agentGetDto);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@DeleteMapping("/agent")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Message>inActiveAgent(@RequestParam long id){
		
		Message message = agentService.inActiveAgent(id);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@PostMapping("/agent")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Message>activeAgent(@RequestParam long id){
		
		Message message = agentService.ActiveAgent(id);
		
		return ResponseEntity.ok(message);
		
	}
	
	

	@GetMapping("/agentDetail")
	ResponseEntity<Agent> getAgentDetails(@RequestParam String username){
		Agent agent=agentService.getAgentDetail(username);
		return ResponseEntity.ok(agent);
	}
	
	@PostMapping("/claim")
	ResponseEntity<Message>claim(@RequestBody AgentClaimDto agentClaimDto){
		Message message=agentService.makeClaim(agentClaimDto);
		return ResponseEntity.ok(message);
		
	}
	
	
	@GetMapping("/allpolicies")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<Page<AccountDto>>getAllAccount(@RequestParam int pageNumber, @RequestParam int pageSize,@RequestParam long id) {
		
		 Pageable pageable=PageRequest.of(pageNumber, pageSize); 

		 Page<AccountDto>acc= agentService.getAllAccounts(pageable,id);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("customer-account", String.valueOf(acc.getTotalElements()));
		 return ResponseEntity.ok().headers(headers).body(acc);
	

	}


}