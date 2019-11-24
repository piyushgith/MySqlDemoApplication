package com.example.test.mysqldemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.test.mysqldemo.controller.UserController;
import com.example.test.mysqldemo.entity.User;
import com.example.test.mysqldemo.repository.UserRepository;
import com.example.test.mysqldemo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

//To test service layer only not good way of doing it.
public class UserControllerTest3 {

	private MockMvc mockMvc;

	@MockBean
	private UserController userController;

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		when(userRepository.findAll()).thenReturn(Stream
				.of(new User(1, "Ram", "Bombay", 20), new User(2, "Rahul", "Patna", 25)).collect(Collectors.toList()));
	}

	@Test
	public void testsayHello() throws Exception {
		this.mockMvc.perform(get("/detail/hi")).andExpect(status().isOk());
	}

	@Test
	public void getEmployeesTest() throws Exception {

		mockMvc.perform(get("/detail/users").content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

		assertEquals(2, userService.getAllUsers().size());

		verify(userRepository, times(1)).findAll();

	}

}
