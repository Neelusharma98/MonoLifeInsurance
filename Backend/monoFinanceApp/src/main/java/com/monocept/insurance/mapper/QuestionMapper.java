package com.monocept.insurance.mapper;

import com.monocept.insurance.dto.QuestionDto;
import com.monocept.insurance.entity.Question;

public class QuestionMapper {
	
public static Question questionDtoToQuestion(QuestionDto questionDto) {
		
		Question question = new Question();
		question.setQuestion(questionDto.getQuestion());
		question.setUsername(questionDto.getUsername());
		question.setAnswer(questionDto.getAnswer());
		question.setActive(questionDto.isActive());
		question.setQuestionId(questionDto.getQuestionId());
		return question;
		
	}
	
	public static QuestionDto questionToQuestionDto(Question question) {
		QuestionDto questionDto=new QuestionDto();
		questionDto.setQuestionId(question.getQuestionId());
		questionDto.setAnswer(question.getAnswer());
		questionDto.setActive(question.isActive());
		questionDto.setQuestion(question.getQuestion());
		questionDto.setUsername(question.getUsername());
		return questionDto;
	}

}
