package com.monocept.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.Employee;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.service.AdminService;
import com.monocept.insurance.service.AgentService;
import com.monocept.insurance.service.CustomerService;
import com.monocept.insurance.service.EmployeeService;

@RequestMapping("/insuranceapp")
@RestController
public class LoginController {
	
	@Autowired
    private AdminService adminService;
	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private AgentService agentService;
	@Autowired
    private CustomerService customerService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
	{
	
	JwtAuthResponse jwtAuthResponce=null;
	
	
	if(loginDto.getRoleType().equals("ROLE_ADMIN"))
	{
		
		System.out.println("dto value is---------- "+loginDto.getRoleType());
	   jwtAuthResponce = adminService.adminLogin(loginDto);
	}
	
	else if(loginDto.getRoleType().equals("ROLE_CUSTOMER"))
	{
		 CustomerGetDto ct =customerService.getcustomerByUsername(loginDto.getUserName());
		 if(ct.getStatus().equals("false"))
			 throw new InsuranceException("Customer not found!");
		System.out.println("dto value is---------- "+loginDto.getRoleType());
	   jwtAuthResponce = customerService.customerLogin(loginDto);

	}
	
	else if(loginDto.getRoleType().equals("ROLE_EMPLOYEE"))
	{
		Employee em = employeeService.getEmployeeByUsername(loginDto.getUserName());
		if(em.isActive()==false)
			throw new InsuranceException("Employee not found!");
		System.out.println("dto value is---------- "+loginDto.getRoleType());
		jwtAuthResponce = employeeService.employeeLogin(loginDto);

	}
	else if(loginDto.getRoleType().equals("ROLE_AGENT"))
	{
		AgentGetDto agent= agentService.getAgentByUsername(loginDto.getUserName());
		if(agent.getStatus().equals("false"))
			throw new InsuranceException("Agent not found!");
		System.out.println("dto value is---------- "+loginDto.getRoleType());
	    jwtAuthResponce = agentService.agentLogin(loginDto);

	}
	
	else
	{
		throw new InsuranceException("RoleType not found!");
	}
	return ResponseEntity.ok(jwtAuthResponce);
	
	}
	

	@PostMapping("/validateUser")
	public ResponseEntity<String> validator(@RequestParam String token)
	{
		return new ResponseEntity<>(adminService.validateuser(token),HttpStatus.OK);
	}
	

}
