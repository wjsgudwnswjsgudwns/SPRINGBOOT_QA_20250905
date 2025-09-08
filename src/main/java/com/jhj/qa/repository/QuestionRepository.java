package com.jhj.qa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.jhj.qa.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public Question findBySubject(@Param("subject") String subject);
	
	public Question findBySubjectAndContent (@Param("subject") String subject, @Param("content") String content);
	
	public List<Question> findBySubjectLike(String keyword);
}
