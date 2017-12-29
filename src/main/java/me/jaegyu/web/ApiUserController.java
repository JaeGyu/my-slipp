package me.jaegyu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.jaegyu.domain.User;
import me.jaegyu.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepository; 

	@GetMapping("/{id}")
	public User show(@PathVariable Long id) {
		return userRepository.findOne(id);
	}
}
