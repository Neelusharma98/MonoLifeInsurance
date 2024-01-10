package com.monocept.insurance.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter 
public class ShowEmployeeDto {
	
    private Long employeeId;
	
    private Double salary;
    
    private Date joiningDate;
	
    private String firstName;
    
    private String lastName;
    
    private String Status;

    private String mobileNumber;

    private String email;

    private Date dateOfBirth;
 
}
