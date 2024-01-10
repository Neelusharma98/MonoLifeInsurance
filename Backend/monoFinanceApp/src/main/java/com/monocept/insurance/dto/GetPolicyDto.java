package com.monocept.insurance.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.Nominee;
import com.monocept.insurance.entity.Payment;
import com.monocept.insurance.entity.PolicyStatus;
import com.monocept.insurance.entity.PremiumType;
import com.monocept.insurance.entity.SubmittedDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GetPolicyDto {
	private long policyId;
	private double premiumAmount;
	private Date issueDate;
	private Date maturityDate;
	private PremiumType premiumType;
	private Double sumAssured;
	private PolicyStatus policyStatus;
	private double investmentAmount;
	private double profitAmount;
	private InsuranceScheme scheme;
	private List<Payment> payments;
	private List<Nominee> nominees;
	private Set<SubmittedDocument> documents;
	

}
