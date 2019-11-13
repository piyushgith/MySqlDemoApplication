package com.example.test.mysqldemo.service;

import java.util.List;
import java.util.Optional;

import com.example.test.mysqldemo.entity.User;

public interface UserService {

	List<User> getAllUsers();

	Optional<User> getUserbyId(int id);
}
