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
public class AgentDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Date dateOfBirth;
    private String houseNo;
    private String apartment;
    private String state;
    private String city;
    private int pincode;
    

}