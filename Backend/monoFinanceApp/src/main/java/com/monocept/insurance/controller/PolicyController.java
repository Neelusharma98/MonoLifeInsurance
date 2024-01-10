package com.monocept.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.GetPolicyDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PaymentDto;
import com.monocept.insurance.dto.PolicyClaimDto;
import com.monocept.insurance.dto.PostPolicyDto;
import com.monocept.insurance.entity.Payment;
import com.monocept.insurance.repository.PolicyRepository;
import com.monocept.insurance.service.PolicyService;

@RestController
@RequestMapping("/insuranceapp")
public class PolicyController {
	
	@Autowired
	private PolicyService policyService;

	@PostMapping("/addpolicy")
	ResponseEntity<Message> addPolicy(@RequestBody PostPolicyDto postPolicyDto) {

		System.out.println(postPolicyDto);

		Message message = policyService.savePolicy(postPolicyDto);

		return ResponseEntity.ok(message);

	}

	@GetMapping("/policy")
	ResponseEntity<GetPolicyDto> getpolicies(@RequestParam int pageNumber, @RequestParam String username) {
		List<GetPolicyDto> policies = policyService.getPolices(username);
		HttpHeaders headers = new HttpHeaders();
		headers.add("policy-Count", String.valueOf(policies.size()));
		return ResponseEntity.ok().headers(headers).body(policies.get(pageNumber));

	}
	
	@GetMapping("/allpolicy")
	ResponseEntity<Page<AccountDto>> getallpolicies(@RequestParam int pageNumber,@RequestParam int pageSize) {
		   Pageable pageable=PageRequest.of(pageNumber, pageSize); 

		 Page<AccountDto>acc= policyService.getAllAccounts(pageable);
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("customer-account", String.valueOf(acc.getTotalElements()));
		 return ResponseEntity.ok().headers(headers).body(acc);

	}
	@PostMapping("/payment")
	ResponseEntity<Message> payment(@RequestBody PaymentDto paymentDto) {
		Message message =  policyService.payment(paymentDto);
		return ResponseEntity.ok().body(message);

	}
	
	@GetMapping("/PendingPolicy")
	ResponseEntity<GetPolicyDto> policy(@RequestParam int pageNumber) {
		List<GetPolicyDto> policies = policyService.getPendingPolices();
		HttpHeaders headers = new HttpHeaders();
		headers.add("policy-Count", String.valueOf(policies.size()));
		return ResponseEntity.ok().headers(headers).body(policies.get(pageNumber));

	}
	
	@GetMapping("/payments")
	ResponseEntity<List<Payment>> payments(@RequestParam Long policyId) {
		List<Payment> policies = policyService.getpayments(policyId);
		return ResponseEntity.ok().body(policies);

	}
	
     
	@GetMapping("/approvePolicy")
	ResponseEntity<Message> aproovPolicy(@RequestParam Long policyId) {
		Message msg = policyService.aproovPolicy(policyId);
		return ResponseEntity.ok().body(msg);

	}
	
	@GetMapping("/rejectPolicy")
	ResponseEntity<Message> RejectPolicy(@RequestParam Long policyId) {
		Message msg = policyService.rejectPolicy(policyId);
		return ResponseEntity.ok().body(msg);

	}
	

	@PostMapping("/claimPolicy")
	ResponseEntity<Message>claimPolicy(@RequestBody PolicyClaimDto policyClaimDto){
		Message message=policyService.policyClaim(policyClaimDto);
		return null;
		
	}
	
}
