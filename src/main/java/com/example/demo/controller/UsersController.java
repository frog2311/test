package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@Controller // Dung cho View html 
@RestController // Dung cho API, JSON 
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UserRepository userRepository;

    // Thêm người dùng mới
    @PostMapping("/save")
    public Users save(@RequestBody Users user){
        if (!userRepository.existsByEmail(user.getEmail())) {
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Email already exists");
        }
    }

}
