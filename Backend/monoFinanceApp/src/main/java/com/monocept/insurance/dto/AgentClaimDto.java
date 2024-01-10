package com.monocept.insurance.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class AgentClaimDto {
	
	private String username;
    private double claimAmount;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String bankAccountNumber;

}
