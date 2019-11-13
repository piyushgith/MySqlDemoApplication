package com.example.test.mysqldemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.test.mysqldemo.entity.User;
import com.example.test.mysqldemo.repository.UserRepository;

@SpringBootApplication
public class MySqlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySqlDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			List<User> userList = new ArrayList<>();
			userList.add(new User(1, "Piyush", "Bangalore", 25));
			userList.add(new User(2, "Ram", "Ayodhya", 21));
			userList.add(new User(3, "Abhijeet", "Patna", 22));
			userList.add(new User(4, "Rahul", "Sydney", 26));

			userList.forEach(x -> userRepository.save(x));
		};
	}
}
