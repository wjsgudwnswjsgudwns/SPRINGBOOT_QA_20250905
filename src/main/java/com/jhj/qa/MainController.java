package com.jhj.qa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value = "/") //root 요청 처리
	public String root() {
		return "redirect:/question/list";
	}
}
