package com.java4rohit.springrolejwt.service;

import com.java4rohit.springrolejwt.model.User;
import com.java4rohit.springrolejwt.model.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
