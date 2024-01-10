package com.monocept.insurance.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.GetPolicyDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PaymentDto;
import com.monocept.insurance.dto.PolicyClaimDto;
import com.monocept.insurance.dto.PostPolicyDto;
import com.monocept.insurance.entity.Payment;

public interface PolicyService
{
	
	Message savePolicy(PostPolicyDto postPolicyDto);

	List<GetPolicyDto> getPolices(String username);

	Message payment(PaymentDto paymentDto);

	List<GetPolicyDto> getPendingPolices();

	List<Payment> getpayments(Long policyId);

	Message aproovPolicy(Long policyId);

	Message rejectPolicy(Long policyId);

	Message policyClaim(PolicyClaimDto policyClaimDto);

	Page<AccountDto> getAllAccounts(Pageable pageable);

	
}