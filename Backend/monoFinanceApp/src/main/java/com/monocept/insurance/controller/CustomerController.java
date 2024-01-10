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
import com.monocept.insurance.dto.AdminGetDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.CustomerPostDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.service.CustomerService;



@RestController
@RequestMapping("insuranceapp")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/addcustomer")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<Message> addCustomer( @RequestBody CustomerPostDto customerDto) {

		Message message = customerService.addcustomer(customerDto);
        
		return ResponseEntity.ok(message);

	}
	
	@GetMapping("/customer")
	//@PreAuthorize("hasRole('CUSTOMER')")
	ResponseEntity<CustomerGetDto> getCustomerByUsername( @RequestParam String username) {

		 CustomerGetDto customerGetDto= customerService.getcustomerByUsername(username);
         
		return ResponseEntity.ok(customerGetDto);

	}
	
	@GetMapping("/allCustomer")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<Page<CustomerGetDto>>getAllCustomers(@RequestParam int pageNumber, @RequestParam int pageSize) {
		
		 Pageable pageable=PageRequest.of(pageNumber, pageSize); 

		 Page<CustomerGetDto>page= customerService.getAllCustomer(pageable);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("customer-count", String.valueOf(page.getTotalElements()));
		 return ResponseEntity.ok().headers(headers).body(page);

		

	}
	
	@GetMapping("/accounts")
	//@PreAuthorize("hasRole('AGENT')")
	ResponseEntity<Page<AccountDto>>getCustomerAccount(@RequestParam int pageNumber, @RequestParam int pageSize,@RequestParam long id) {
		
		 Pageable pageable=PageRequest.of(pageNumber, pageSize); 

		 Page<AccountDto>acc= customerService.getCustomerAccounts(pageable,id);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("customer-account", String.valueOf(acc.getTotalElements()));
		 return ResponseEntity.ok().headers(headers).body(acc);
	

	}
	
	@PutMapping("/customer")
	//@PreAuthorize("hasRole('CUSTOMER')")
	ResponseEntity<Message>editCustomer(@RequestBody EditProfileDto editProfileDto ){
		
		 Message message= customerService.editCustomer(editProfileDto);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@DeleteMapping("/customer")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Message>inActiveCustomer(@RequestParam long id){
		
		Message message = customerService.inActiveCustomer(id);
		
		return ResponseEntity.ok(message);
		
	}
	
	
	@PostMapping("/customer")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Message>activeCustomer(@RequestParam long id){
		
		Message message = customerService.ActiveCustomer(id);
		
		return ResponseEntity.ok(message);
		
	}

}
