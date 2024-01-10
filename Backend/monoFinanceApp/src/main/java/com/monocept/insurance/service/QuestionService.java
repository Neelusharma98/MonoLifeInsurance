package com.monocept.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.QuestionDto;

public interface QuestionService {

	Message questionPost(QuestionDto questionDto);

	Message questionPut(QuestionDto questionDto);

	Page<QuestionDto> questionGet(Pageable pageable);

}
