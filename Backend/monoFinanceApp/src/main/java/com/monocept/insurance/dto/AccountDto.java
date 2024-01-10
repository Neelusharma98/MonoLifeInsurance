package com.monocept.insurance.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Claim;
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
import lombok.ToString;


@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AccountDto {
	
	
    private Long policyNo;
    private String username;
    private String insuranceScheme;

    private Date issueDate;

    private Date maturityDate;
    
    private String premiumType;

    private Double sumAssured;

    private Double premiumAmount;

    private String status;

    private List<Nominee> nominees;

    private List<Payment> payments;
    
    private Claim claims;

    private Set<SubmittedDocument> submittedDocuments = new HashSet<>();
      

}
