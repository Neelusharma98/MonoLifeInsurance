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
public class AgentGetDto {
	private double id;
	private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Date dateOfBirth;
    private double commission;
    private String status;

}