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
import me.jaegyu.domain.Result;
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

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요 합니다. ");
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		if (!question.isSameWriter(sessionedUser)) {
			throw new IllegalStateException("자신이 쓴 글만, 수정 삭제가 가능 합니다.");
		}

		return true;
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		model.addAttribute("question", repository.findOne(id));
		return "/qna/show";
	}

	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요 합니다. ");
			// throw new IllegalStateException("로그인이 필요 합니다. ");
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		if (!question.isSameWriter(sessionedUser)) {
			return Result.fail("자신이 쓴 글만, 수정 삭제가 가능 합니다.");
			// throw new IllegalStateException("자신이 쓴 글만, 수정 삭제가 가능 합니다.");
		}

		return Result.ok();
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = repository.findOne(id);

		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";

	}

	@PutMapping("/{id}")
	public String update(@PathVariable("id") Long id, String title, String contents, HttpSession session, Model model) {
		Question question = repository.findOne(id);

		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		question.update(title, contents);
		repository.save(question);
		return String.format("redirect:/questions/%d", id);

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id, HttpSession session, Model model) {
		Question question = repository.findOne(id);

		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		repository.delete(id);
		return "redirect:/";

	}

}
