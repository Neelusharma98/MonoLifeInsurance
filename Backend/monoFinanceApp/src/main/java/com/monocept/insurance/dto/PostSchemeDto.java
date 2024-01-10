package com.monocept.insurance.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PostSchemeDto {
	
	private String schemeName;
	private String schemeImage;
	private String description;
	private int minAge;
	private int maxAge;
	private int minAmount;
	private int maxAmount;
	private int minDuration;
	private int maxDuration;
	private double profitRatio;
	private double regCommRatio;
	private double instCommRatio;
	private Set<String> requierdDocs;

}
