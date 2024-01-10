package com.monocept.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PaymentDto;
import com.monocept.insurance.service.PolicyService;

public class PaymentController {
	
	@Autowired
	private PolicyService insurancePolicyService;
	
	@PostMapping("/payment")
	//@PreAuthorize("hasRole('CUSTOMER')")
	ResponseEntity<Message> addPolicy(@RequestBody PaymentDto paymentDto) {
		
		System.out.println(paymentDto);

		//Message message = insurancePolicyService.makePayment(paymentDto);

		//return ResponseEntity.ok(message);
		return null;

	}


}
