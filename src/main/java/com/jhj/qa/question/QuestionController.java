package com.jhj.qa.question;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.jhj.qa.answer.AnswerForm;
import com.jhj.qa.user.SiteUser;
import com.jhj.qa.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/question") //prefix(접두사)
@Controller
public class QuestionController {
	
//	@Autowired
//	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/list")
	//@ResponseBody
	public String list(Model model) {
		
		//List<Question> questionList = questionRepository.findAll(); //모든 질문글 불러오기
		List<Question> questionList = questionService.getList();
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}	
	
//	@GetMapping(value = "/list")
//	//@ResponseBody
//	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//		
//		//List<Question> questionList = questionRepository.findAll(); //모든 질문글 불러오기
//		Page<Question> paging = questionService.getList(page); // 게시글 10개씩 자른 리스트
//		model.addAttribute("paging", paging);
//		
//		return "question_list";
//	}	
	
	
	@GetMapping(value = "/detail/{id}") //파라미터이름 없이 값만 넘어왔을때 처리
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		
		questionService.hit(questionService.getQuestion(id));
		
		//service에 3을 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail"; //타임리프 html의 이름
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/create")
	public String questionCreate(QuestionForm questionForm) {
			
		return "question_form"; // 질문 등록하는 폼 페이지
	}
	
//	@PostMapping(value = "/create") // 질문 내용을 DB에 저장하는 메소드
//	public String questionCreate(@RequestParam(value =  "subject") String subject, @RequestParam(value = "content") String content) {
//		questionService.create(subject, content);
//		
//		return "redirect:/question/list";
//	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create") // 질문 내용을 DB에 저장하는 메소드(with validation)
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) { // 참이면 에러 발생 -> 유효성 체크
			return "question_form"; // 에러 발생시 다시 질문 등록 폼으로 이동
		}
		SiteUser siteUser = userService.getUser(principal.getName());
		
		questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/modify/{id}")
	public String questionModify(QuestionForm questionForm ,@PathVariable("id") Integer id, Principal principal) {
		Question question = questionService.getQuestion(id); // id에 해당하는 엔티티 반환
		
		//글쓴 유저와 로그인한 유저의 동일 여부 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정 권한이 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		// question_form에 questionForm의 subject와 content를 value로 출력하는 기능이 이미 구현되어 있으므로
		// 해당 폼을 재활용
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = questionService.getQuestion(id);
		
		//글쓴 유저와 로그인한 유저의 동일 여부 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정 권한이 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
		Question question = questionService.getQuestion(id); // id로 객체 가져오기
		
		//글쓴 유저와 로그인한 유저의 동일 여부 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정 권한이 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		questionService.delete(question);
		
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/vote/{id}")
	public String vote(@PathVariable("id") Integer id, Principal principal) {
		Question question = questionService.getQuestion(id);
		SiteUser siteUser = userService.getUser(principal.getName());
		//로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		
		questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/disvote/{id}")
	public String disvote(@PathVariable("id") Integer id, Principal principal) {
		Question question = questionService.getQuestion(id);
		SiteUser siteUser = userService.getUser(principal.getName());
		//로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		
		questionService.disvote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
}
