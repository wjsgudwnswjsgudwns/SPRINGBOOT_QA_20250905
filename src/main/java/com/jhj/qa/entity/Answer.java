package com.jhj.qa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 기본키, 자동 증가
	
	@Column(columnDefinition = "TEXT")
	private String content; // 답변 게시판 내용
	
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@ManyToOne
	private Question question;
	// N:1 관계 -> 답변:질문
}
