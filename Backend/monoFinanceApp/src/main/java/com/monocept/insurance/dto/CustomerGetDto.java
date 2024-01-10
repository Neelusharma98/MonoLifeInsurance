package com.monocept.insurance.dto;

import java.util.Date;

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

public class CustomerGetDto {
    
	private long Id;
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;
	private String status;
	private Date dateOfBirth;
}
