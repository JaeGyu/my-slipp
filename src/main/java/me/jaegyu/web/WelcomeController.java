package me.jaegyu.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	
	/*
	 * 아래는 welcome라는 핸들러이다. 
	 * */
	@GetMapping("/helloworld")
	public String welcome(@RequestParam("name") String name, int age,  Model model){
		System.out.println("name : " + name + ", age : " + age);
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}
}
