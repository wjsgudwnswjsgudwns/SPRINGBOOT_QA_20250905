package com.jhj.qa.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreateForm {

	@NotEmpty(message = "아이디를 입력해주세요.")
	@Size(min = 3, max = 25, message = "아이디는 3~25자 사이입니다.") // 아이디 길이 3~25자 제한
	private String username;
	
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인을 입력해주세요.")
	private String password2;
	
	@NotEmpty(message = "이메일을 입력해주세요.")
	@Email // 이메일 형식이 아니면 에러
	private String email;
}
