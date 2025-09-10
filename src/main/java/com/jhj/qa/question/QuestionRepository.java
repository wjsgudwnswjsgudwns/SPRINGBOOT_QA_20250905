package com.jhj.qa.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public Question findBySubject(@Param("subject") String subject);
	
	public Question findBySubjectAndContent (@Param("subject") String subject, @Param("content") String content);
	
	public List<Question> findBySubjectLike(String keyword);
	
	//페이징 관련
	//public Page<Question> findAll(Pageable pageable);
	
}
