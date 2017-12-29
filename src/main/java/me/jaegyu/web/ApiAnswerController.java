package me.jaegyu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.jaegyu.domain.Answer;
import me.jaegyu.domain.AnswerRepository;
import me.jaegyu.domain.Question;
import me.jaegyu.domain.QuestionRepository;
import me.jaegyu.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	
	@Autowired
	private QuestionRepository qRepository;

	@Autowired
	private AnswerRepository repository;

	@PostMapping("")
	public Answer create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = qRepository.findOne(questionId);

		Answer answer = new Answer(loginUser, question, contents);
		return repository.save(answer);
		
	}

}
