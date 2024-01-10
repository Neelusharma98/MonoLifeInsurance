package com.monocept.insurance.dto;



import java.sql.Date;

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
public class EditProfileDto {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String mobile;
	private String email;
   
}
