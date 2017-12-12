package me.jaegyu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository repository;

	@RequestMapping("/")
	public String home(Model model){
		model.addAttribute("questions", repository.findAll());
		return "index";
	}
}
