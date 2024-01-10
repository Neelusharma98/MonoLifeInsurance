package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
