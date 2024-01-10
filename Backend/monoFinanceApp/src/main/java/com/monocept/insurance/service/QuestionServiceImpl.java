package com.monocept.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.QuestionDto;
import com.monocept.insurance.entity.Question;
import com.monocept.insurance.mapper.QuestionMapper;
import com.monocept.insurance.repository.QueryRepository;
import com.monocept.insurance.repository.QuestionRepository;


@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Message questionPost(QuestionDto questionDto) {
		
		Question question=QuestionMapper.questionDtoToQuestion(questionDto);
		questionRepository.save(question);
		return new Message(HttpStatus.OK,"Question Saved Successfully");
	}

	@Override
	public Page<QuestionDto> questionGet(Pageable pageable) {
		
		Page<Question>questions=questionRepository.findAll(pageable);
		
		Page<QuestionDto> questionList = questions.map(question -> QuestionMapper.questionToQuestionDto(question));
		
		return questionList;
		
		
	}

	@Override
	public Message questionPut(QuestionDto questionDto) {
		Question question=QuestionMapper.questionDtoToQuestion(questionDto);
		questionRepository.save(question);
		return new Message(HttpStatus.OK,"Question Updated Successfully");
	}
	
	
	

}
