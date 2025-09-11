package com.jhj.qa.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jhj.qa.DataNotFoundException;
import com.jhj.qa.answer.Answer;
import com.jhj.qa.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
	
	public Page<Question> getPageQuestion(int page, String kw) { //모든 질문글 가져오기 -> 페이징
		
		
		int size =  10; // 페이지 당 10개씩 글 출력
		
		int startRow = page * size; 
		int endRow= (page + 1) * size;
		
		
		// 검색어 없이 리스트 조회
		List<Question> pageQuestionList = questionRepository.findQuestionsWithPaging(startRow, endRow);
		long totalQuestion = questionRepository.count();
		
		// 검색 결과를 조회하여 리스트 조회
		List<Question> searchQuestionList = questionRepository.searchQuestionsWithPaging(kw, startRow, endRow);
		int totalSearchQuestion = questionRepository.countSearchResult(kw);
		
		Page<Question> pagingList = new PageImpl<>(searchQuestionList, PageRequest.of(page, size),totalSearchQuestion);
		
		return pagingList;
	}
	
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
	
	public void disvote(Question question, SiteUser siteUser) {
		question.getDisvoter().add(siteUser);
		questionRepository.save(question);
	}
	
	private Specification<Question> search(String kw) { // 키워드 (kw) 로 조회
		return new Specification<>() {
			
			private static final long serialVersionUID = 1L; // 변조 방지
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
				
			}
		};
	}
	
	
	
}
