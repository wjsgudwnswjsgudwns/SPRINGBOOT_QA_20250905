package com.jhj.qa.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm { // 질문글의 제목과 내용의 유효성 체크
	
	@NotEmpty(message = "제목을 입력해주세요") // 공란 입력시 작동
	@Size(max = 200) // 제목이 최대 200글자까지 허용
	private String subject;
	
	@NotEmpty(message = "내용을 입력해주세요") // 공란 입력시 작동
	@Size(max = 2000) // 제목이 최대 2000글자까지 허용 
	private String content;

}
