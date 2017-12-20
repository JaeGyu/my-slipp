package me.jaegyu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.Question;
import me.jaegyu.domain.QuestionRepository;
import me.jaegyu.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository repository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = new Question(sessionedUser, title, contents);

		repository.save(question);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		model.addAttribute("question", repository.findOne(id));
		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = repository.findOne(id);
		if(!question.sameWriter(sessionedUser)){
			return "redirect:/users/loginForm";
		}
		
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable("id") Long id, String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = repository.findOne(id);
		if(!question.sameWriter(sessionedUser)){
			return "redirect:/users/loginForm";
		}
		
//		Question question = repository.findOne(id);
		question.update(title, contents);
		repository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = repository.findOne(id);
		if(!question.sameWriter(sessionedUser)){
			return "redirect:/users/loginForm";
		}
		
		repository.delete(id);
		return "redirect:/";
	}

}
