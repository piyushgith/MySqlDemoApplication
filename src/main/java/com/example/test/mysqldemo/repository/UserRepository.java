package com.example.test.mysqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.mysqldemo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
