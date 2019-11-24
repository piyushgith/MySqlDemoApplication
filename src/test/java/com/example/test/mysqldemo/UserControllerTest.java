package com.example.test.mysqldemo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.test.mysqldemo.entity.User;
import com.example.test.mysqldemo.repository.UserRepository;
import com.example.test.mysqldemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setup() {
		when(userRepository.findAll()).thenReturn(Stream
				.of(new User(1, "Ram", "Bombay", 20), new User(2, "Rahul", "Patna", 25)).collect(Collectors.toList()));
	}

	@Test
	public void test_sayHello() throws Exception {
		this.mockMvc.perform(get("/hi")).andExpect(status().isOk());
	}

	@Test
	public void test_getUser() throws Exception {

		mockMvc.perform(get("/users/data"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Ram")))
                .andExpect(jsonPath("$[0].address", is("Bombay")))
                .andExpect(jsonPath("$[0].age", is(20)))
				//working you can verify 1st only
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Rahul")))
                .andExpect(jsonPath("$[1].address", is("Patna")))
                .andExpect(jsonPath("$[1].age", is(25)));

		verify(userRepository, times(1)).findAll();

	}

	@Test
    public void test_saveUserData() throws Exception {

		User user = new User(14, "Rohit", "Delhi", 22);

		when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/users/data/save")
                .content(om.writeValueAsString(user))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(14)))
                .andExpect(jsonPath("$.name", is("Rohit")))
                .andExpect(jsonPath("$.address", is("Delhi")))
                .andExpect(jsonPath("$.age", is(22)));

        verify(userRepository, times(1)).save(user);
    }
	
    @Test
    public void test_updateUserData() throws Exception {

		User user = new User(14, "Rohit", "Delhi", 22);

		when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(put("/users/data/update")
                .content(om.writeValueAsString(user))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(14)))
                .andExpect(jsonPath("$.name", is("Rohit")))
                .andExpect(jsonPath("$.address", is("Delhi")))
                .andExpect(jsonPath("$.age", is(22)));
		
        verify(userRepository, times(1)).save(user);

    }
	
	
    @Test
	public void test_deleteuser_OK() throws Exception {

		doNothing().when(userRepository).deleteById(1);

		mockMvc.perform(delete("/users/data/delete/1")).andExpect(status().isOk());

		verify(userRepository, times(1)).deleteById(1);
	}
}
