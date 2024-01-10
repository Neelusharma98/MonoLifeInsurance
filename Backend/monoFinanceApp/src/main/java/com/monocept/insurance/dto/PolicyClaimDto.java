package com.monocept.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PolicyClaimDto {

	private long policyId;
    private double claimAmount;
    private String bankName;
    private String branchName;
    private String bankAccountNumber;
    private String ifscCode;


}
