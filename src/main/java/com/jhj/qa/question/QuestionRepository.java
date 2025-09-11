package com.jhj.qa.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public Question findBySubject(@Param("subject") String subject);
	
	public Question findBySubjectAndContent (@Param("subject") String subject, @Param("content") String content);
	
	public List<Question> findBySubjectLike(String keyword);
	
	//페이징 관련
	//public Page<Question> findAll(Pageable pageable);
	
//	@Query(
//			value = "UPDATE question SET hit=hit+1 WHERE id = :id"
//			)
//	public void updateHit(@Param("id") Integer id);
	
	@Query(
	         value = "SELECT * FROM ( " +
	                 " SELECT q.*, ROWNUM rnum FROM ( " +
	                 "   SELECT * FROM question ORDER BY createdate DESC " +
	                 " ) q WHERE ROWNUM <= :endRow " +
	                 ") WHERE rnum > :startRow",
	         nativeQuery = true)
	    List<Question> findQuestionsWithPaging(@Param("startRow") int startRow,
	                                           @Param("endRow") int endRow);
	
}
