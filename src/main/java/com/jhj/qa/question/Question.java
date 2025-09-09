package com.jhj.qa.question;

import java.time.LocalDateTime;
import java.util.List;

import com.jhj.qa.answer.Answer;
import com.jhj.qa.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "question") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "QUESTION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "QUESTION_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR")
	private Integer id; // 질문 게시판의 글번호 (기본키, 자동 번호 증가)
	
	@Column(length = 200)
	private String subject; // 질문게시판 제목
	
	@Column(length = 2000) // 
	private String content; // 질문게시판 내용
	
	private LocalDateTime createdate; // 질문게시판 등록일
	
	// 1:N 관계 -> 질문 : 답변들
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 질문글이 삭제되면 질문글에 달린 답변글도 같이 사라져줘야함.
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;
}
