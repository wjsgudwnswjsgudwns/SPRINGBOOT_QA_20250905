package com.jhj.qa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//	@GetMapping(value = "/") // local root 요청 처리
//	public String root() {
//		return "redirect:/question/list";
//	}
	
	@GetMapping(value = "/jboard") // cloud root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}
}
