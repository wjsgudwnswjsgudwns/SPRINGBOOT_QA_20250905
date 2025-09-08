package com.jhj.qa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jhj.qa.answer.Answer;
import com.jhj.qa.answer.AnswerRepository;
import com.jhj.qa.question.Question;
import com.jhj.qa.question.QuestionRepository;

@SpringBootTest
public class Test02 {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Test
//	@DisplayName("질문 게시판 수정")
//	public void testJpa1() {
//		Optional<Question> questionOptional = questionRepository.findById(3);
//		assertTrue(questionOptional.isPresent()); // 기본키로 가져온 레코드가 존재하면 true
//		// 만약 false 반환시 그 즉시 테스트 종료
//		
//		Question question = questionOptional.get();
//		question.setSubject("수정된 제목");
//		questionRepository.save(question);
//	}
	
//	@Test
//	@DisplayName("질문 삭제")
//	public void testJpa2() {
//		assertEquals(2, questionRepository.count());
//		// 모든 레코드의 갯수를 반환
//		
//		Optional<Question> questionOptional = questionRepository.findById(3);
//		assertTrue(questionOptional.isPresent());
//		
//		Question question = questionOptional.get();
//		questionRepository.delete(question); // delete(엔티티) 해당 글 삭제
//		
//		assertEquals(1, questionRepository.count());
//	}
	
//	@Test
//	@DisplayName("게시판 답변 글 작성")
//	public void testJpa3() {
//		// 질문글 번호를 알아내야함
//		Optional<Question> questionOptional = questionRepository.findById(4);
//		assertTrue(questionOptional.isPresent());
//		Question question = questionOptional.get();
//		
//		Answer answer = new Answer();
//		answer.setContent("네 자동으로 생성됩니다.");
//		answer.setCreatedate(LocalDateTime.now());
//		answer.setQuestion(question); // 질문글 번호 넣어주기
//		answerRepository.save(answer);
//	}

	@Test
	@DisplayName("답변 게시판 글 조회")
	public void testJpa4() {
		Optional<Answer> answerOptional = answerRepository.findById(1);
		assertTrue(answerOptional.isPresent());
		Answer answer = answerOptional.get();
		
		assertEquals(4, answer.getQuestion().getId());
	}
	
	@Test
	@Transactional
	@DisplayName("질문글의 답변들 조회")
	public void testJpa5() {
		// 질문글 찾기
		Optional<Question> questionOptional = questionRepository.findById(4);
		assertTrue(questionOptional.isPresent());
		Question question = questionOptional.get();
		
		List<Answer> answerList = question.getAnswerList();
		// 해당 질문글에 달린 답변 글들
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
		
	}
	
}
