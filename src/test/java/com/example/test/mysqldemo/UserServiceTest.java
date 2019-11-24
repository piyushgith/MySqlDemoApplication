package com.example.test.mysqldemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.test.mysqldemo.entity.User;
import com.example.test.mysqldemo.repository.UserRepository;
import com.example.test.mysqldemo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void getUserTest() {
		when(userRepository.findAll()).thenReturn(Stream
				.of(new User(1, "Ram", "Bombay", 20), new User(2, "Rahul", "Patna", 25)).collect(Collectors.toList()));
		assertEquals(2, userService.getAllUsers().size());
		// you can verify only mocked objects
		verify(userRepository, times(1)).findAll();
	}
}
