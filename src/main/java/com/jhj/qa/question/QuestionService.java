package com.jhj.qa.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jhj.qa.DataNotFoundException;
import com.jhj.qa.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
//	@Autowired
	private final QuestionRepository questionRepository; 
	// @RequiredArgsConstructor에 의해 생성자 방식으로 주입된 questionRepostitory(final 필드만 가능)
	
	public List<Question> getList() { //모든 질문글 가져오기
		return questionRepository.findAll();
	}
	
//	public Page<Question> getList(int page) { //모든 질문글 가져오기 -> 페이징
//		Pageable pageable = PageRequest.of(page, 10); // 한페이지당 10개씩 표시
//		
//		return questionRepository.findAll(pageable);
//	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> qOptional = questionRepository.findById(id);
		
		if(qOptional.isPresent()) {
			return qOptional.get(); //question 반환
		} else {
			throw new DataNotFoundException("question not found");
		}
		
	}
	
	public void create(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		question.setAuthor(user);
		questionRepository.save(question);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject); // 새로운 제목
		question.setContent(content); // 새로운 내용
		question.setModifydate(LocalDateTime.now()); // 수정일
		
		questionRepository.save(question); // 수정
	}
	
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		questionRepository.save(question);
	}
	
	public void hit(Question question) {
		question.setHit(question.getHit() + 1);
		questionRepository.save(question);
	}
	
}
