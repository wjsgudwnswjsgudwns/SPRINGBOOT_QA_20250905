package com.jhj.qa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jhj.qa.entity.Question;
import com.jhj.qa.repository.AnswerRepository;
import com.jhj.qa.repository.QuestionRepository;

@SpringBootTest
public class Test01 {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Test
//	public void testJpa1() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해 알고 싶습니다.");
//		q1.setCreatedate(LocalDateTime.now());
//		questionRepository.save(q1);
//		
//		Question q2 = new Question();
//		q2.setSubject("스프링 부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreatedate(LocalDateTime.now());
//		questionRepository.save(q2);
//	}
	
	@Test
	@DisplayName("모든 질문글 불러오기")
	public void testJpa2() {
		List<Question> allQuestion = questionRepository.findAll();
		assertEquals(2, allQuestion.size()); // 예상 결과 확인하기 -> allQuestion의 크기가 2인지 확인 (기댓값, 실제값)
		
		Question question = allQuestion.get(0);
		assertEquals("sbb가 무엇인가요?", question.getSubject());
		
	}
	
	@Test
	@DisplayName("질문글 번호(기본키)로 조회")
	public void testJpa3() {
		Optional<Question> qOptional = questionRepository.findById(3);
		
		if(qOptional.isPresent()) {
			Question question = qOptional.get();
			assertEquals("sbb가 무엇인가요?", question.getSubject());
		}
		
	}
}
