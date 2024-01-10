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
public class GetSchemeDetailDto {
	
	
	private String schemeImage;
	private String description;
	private int minAge;
	private int maxAge;
	private double minAmount;
	private double maxAmount;
	private int minDuration;
	private int maxDuration;
	private Set<String> requierdDocs;

}
