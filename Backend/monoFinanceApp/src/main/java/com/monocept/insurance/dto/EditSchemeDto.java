package com.monocept.insurance.dto;

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
public class EditSchemeDto {

	private Long schemeId;
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
}
