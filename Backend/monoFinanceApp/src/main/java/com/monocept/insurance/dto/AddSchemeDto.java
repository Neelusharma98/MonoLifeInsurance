package com.monocept.insurance.dto;

import java.util.Set;

import com.monocept.insurance.entity.SchemeDocument;

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
public class AddSchemeDto {
	
	private Long planId;
	private String schemeName;
	private String schemeImage;
	private String description;
	private Double minAmount;
	private Double maxAmount;
	private int minInvestmentTime;
	private int maxInvestmentTime;
	private int minAge;
	private int maxAge;
	private Double profitRatio;
    private Double registrationCommRatio;
    private Double installmentCommRatio;
    private Set<Integer>documents;
	 
}
