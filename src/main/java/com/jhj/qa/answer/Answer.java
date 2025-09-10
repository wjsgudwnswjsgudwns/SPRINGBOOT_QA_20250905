package com.jhj.qa.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.jhj.qa.question.Question;
import com.jhj.qa.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "ANSWER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "ANSWER_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_GENERATOR")
	private Integer id; // 기본키, 자동 증가
	
	@Column(length = 2000)
	private String content; // 답변 게시판 내용
	
	private LocalDateTime createdate;
	
	@ManyToOne
	private Question question;
	// N:1 관계 -> 답변:질문	
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifydate; // 답변글 수정일
	
	// 질문:추천
	@ManyToMany
	Set<SiteUser> voter; // 추천한 유저가 중복 없이 저장 -> 유저수 -> 추천수
	
	@ManyToMany
	Set<SiteUser> disvoter;
}
