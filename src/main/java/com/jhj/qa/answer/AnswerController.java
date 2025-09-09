package com.jhj.qa.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhj.qa.question.Question;
import com.jhj.qa.question.QuestionService;
import com.jhj.qa.user.SiteUser;
import com.jhj.qa.user.UserService;
import jakarta.validation.Valid;

@RequestMapping("/answer")
@Controller
public class AnswerController {

   // private final UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService userService;

	
    AnswerController(UserService userService) {
        this.userService = userService;
    }
	
//	@PostMapping(value = "/create/{id}") //답변 등록 요청->오는 파라미터 값 : 부모 질문글의 번호
//	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam("content") String content) {
//		Question question = questionService.getQuestion(id);		
//		
//		answerService.create(question, content); //DB에 답변 등록
//		
//		return String.format("redirect:/question/detail/%s", id);
//	}
    
    @PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create/{id}") //답변 등록 요청->오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = questionService.getQuestion(id);
		
		//principal.getName(); // 로그인한 유저 아이디 얻기
		SiteUser siteUser = userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		answerService.create(question, answerForm.getContent(), siteUser); //DB에 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);
	}
}
