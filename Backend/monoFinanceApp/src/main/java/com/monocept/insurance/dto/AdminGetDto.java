package com.monocept.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AdminGetDto {
	private long Id;
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;

}
