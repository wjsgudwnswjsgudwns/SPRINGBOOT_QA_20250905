package com.jhj.qa.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhj.qa.answer.AnswerForm;

import jakarta.validation.Valid;

@RequestMapping("/question") //prefix(접두사)
@Controller
public class QuestionController {
	
//	@Autowired
//	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping(value = "/") //root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}
	
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
		
		//service에 3을 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail"; //타임리프 html의 이름
	}
	
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
	
	@PostMapping(value = "/create") // 질문 내용을 DB에 저장하는 메소드(with validation)
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) { // 참이면 에러 발생 -> 유효성 체크
			return "question_form"; // 에러 발생시 다시 질문 등록 폼으로 이동
		}
		questionService.create(questionForm.getSubject(), questionForm.getContent());
		
		return "redirect:/question/list";
	}
	
	
}
