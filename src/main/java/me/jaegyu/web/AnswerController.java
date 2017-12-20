package me.jaegyu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.Answer;
import me.jaegyu.domain.AnswerRepository;
import me.jaegyu.domain.Question;
import me.jaegyu.domain.QuestionRepository;
import me.jaegyu.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	private QuestionRepository qRepository;

	@Autowired
	private AnswerRepository repository;

	@PostMapping("")
	public String create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = qRepository.findOne(questionId);

		Answer answer = new Answer(loginUser, question, contents);

		repository.save(answer);

		return String.format("redirect:/questions/%d", questionId);
	}
}
