package com.jhj.qa.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "siteuser") // 실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "USER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName =  "USER_SEQ", // 실제 DB 시퀸스 이름
		initialValue = 1, // 시퀀스 초기값
		allocationSize = 1 // 시퀀스 증가치
		)
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	private Long id; // 유저 번호, 기본키
	
	@Column(unique = true) // 유저 아이디 중복 불가
	private String username; // 유저 아이디
	
	
	private String password; // 패스워드
	
	@Column(unique = true)
	private String email; // 이메일
}
