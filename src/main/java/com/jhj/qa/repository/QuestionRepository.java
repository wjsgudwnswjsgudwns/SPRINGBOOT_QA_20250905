package com.jhj.qa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.jhj.qa.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public Question findBySubject(@Param("subject") String subject);
}
