package com.example.test.mysqldemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.mysqldemo.entity.User;
import com.example.test.mysqldemo.service.UserService;

@RestController
@RequestMapping(path = "/detail")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/hi")
	public String sayHello() {
		return "Hi There !!";
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserbyId(@PathVariable("id") int userId) {
		// return new ResponseEntity<User>(userService.getUserbyId(userId).get(),HttpStatus.OK);

		return userService.getUserbyId(userId).map(data -> {
			return ResponseEntity.ok().body(data);
		}).orElseGet(() -> {
			return ResponseEntity.badRequest().body(null);
		});

	}

}
