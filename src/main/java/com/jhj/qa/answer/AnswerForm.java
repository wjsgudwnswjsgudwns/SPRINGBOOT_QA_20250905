package com.jhj.qa.answer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	@NotNull(message = "내용을 입력해주세요.")
	@Size(min = 5, message = "최소 5글자 이상 입력하세요")
	private String content;
}
