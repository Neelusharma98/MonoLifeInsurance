package com.monocept.insurance.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class QuestionDto {

	
	private Long questionId;
	
	private String username;

	private String question;

	private String answer;

	private boolean active = true;
}
