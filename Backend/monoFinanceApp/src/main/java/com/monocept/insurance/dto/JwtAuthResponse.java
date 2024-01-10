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
public class JwtAuthResponse {
	
	private String accessToken;
	private String tokenType = "Bearer";
	private String username;
	private String roleType;

}
