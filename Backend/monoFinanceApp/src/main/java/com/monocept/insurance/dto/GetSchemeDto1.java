package com.monocept.insurance.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GetSchemeDto1 {
	
	private long schemeId;
	private String schemeName;
	private String schemeImage;
	private String description;
	private int minAge;
	private int maxAge;
	private double minAmount;
	private double maxAmount;
	private int minDuration;
	private int maxDuration;
	private Double profitRatio;
    private Double registrationCommRatio;
    private Double installmentCommRatio;
	private Set<String> requierdDocs;

}
