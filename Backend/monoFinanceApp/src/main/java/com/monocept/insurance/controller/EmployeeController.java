package com.monocept.insurance.controller;

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

import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.ShowEmployeeDto;
import com.monocept.insurance.entity.Employee;
import com.monocept.insurance.service.EmployeeService;

@RestController
@RequestMapping("/insuranceapp")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addemployee")
	public ResponseEntity<Message> addEmployee(@RequestBody AddEmployeeDto addEmployeeDto)
	{   System.out.println("inside addEmployee"+addEmployeeDto);
		Message msg = employeeService.addEmployee(addEmployeeDto);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	ResponseEntity<Employee> getEmployeeByUsername( @RequestParam String username) {

		 Employee emp= employeeService.getEmployeeByUsername(username);

		return ResponseEntity.ok(emp);

	}

	//@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/employee")
	public ResponseEntity<Message> editEmployee(@RequestBody EditProfileDto editProfileDto)
	{   System.out.println("inside addEmployee"+editProfileDto);
		Message msg = employeeService.editEmployee(editProfileDto);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allemployee")
	public ResponseEntity<Page<ShowEmployeeDto>> getAllEmployee(@RequestParam int pageNumber, @RequestParam int pageSize)
	{  
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<ShowEmployeeDto> employee= employeeService.getAllEmployee(pageable);
		System.out.println("total employees are --"+employee.getTotalElements());
		HttpHeaders headers = new HttpHeaders();
	    headers.add("employee-Count", String.valueOf(employee.getTotalElements()));
	    return ResponseEntity.ok().headers(headers).body(employee);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/employee")
	public ResponseEntity<Message> activeEmployee(@RequestParam Long employeeId)
	{   System.out.println("inside addEmployee"+employeeId);
		Message msg = employeeService.activeEmployee(employeeId);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/employee")
	public ResponseEntity<Message> inActiveEmployee(@RequestParam Long employeeId)
	{   System.out.println("inside addEmployee"+employeeId);
		Message msg = employeeService.inActiveEmployee(employeeId);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}


}
