package com.jhj.qa.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 질문 게시판의 글번호 (기본키, 자동 번호 증가)
	
	@Column(length = 200)
	private String subject; // 질문게시판 제목
	
	@Column(columnDefinition = "TEXT") // 
	private String content; // 질문게시판 내용
	
	@CreationTimestamp
	private LocalDateTime createDate; // 질문게시판 등록일
	
	// 1:N 관계 -> 질문 : 답변들
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 질문글이 삭제되면 질문글에 달린 답변글도 같이 사라져줘야함.
	private List<Answer> answerList;
}
