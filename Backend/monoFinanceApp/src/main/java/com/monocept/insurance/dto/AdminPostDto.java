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
public class AdminPostDto {
	
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String mobile;
	private String email;
    private String username;
    private String password;
    private String houseNo;
    private String apartment;
    private String city;
    private int pincode;
    private String state;
}
