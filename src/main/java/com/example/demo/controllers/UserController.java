package com.example.demo.controllers;


import com.example.demo.dto.User;
import com.example.demo.dto.UserLogin;
import com.example.demo.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        return userServices.registerUser(user);
    }
    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLogin userLogin) {
        return userServices.loginUser(userLogin);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return userServices.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return userServices.getUser(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user,@PathVariable long id) {
        return userServices.updateUser(user,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        return userServices.deleteUser(id);
    }
}
