package com.example.demo.services;

import com.example.demo.dto.User;
import com.example.demo.dto.UserLogin;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;



    public ResponseEntity<String> registerUser(User user) {
        Optional<User> userExist = userRepository.findByEmail(user.getEmail());
        if (userExist.isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered, Please login!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


    public ResponseEntity<String> loginUser(UserLogin userLogin) {
        Optional<User> userExist = userRepository.findByEmail(userLogin.getEmail());
        if (userExist.isEmpty()) {
            return ResponseEntity.badRequest().body("Email does not exist");
        }
        if (userLogin.getPassword().equals(userExist.get().getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Incorrect password");
        }
    }


    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getUser(Long id) {
       Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    public ResponseEntity<String> updateUser(User user, Long id) {
        Optional<User> userExist = userRepository.findById(id);
        if (userExist.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }

        User existingUser = userExist.get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setNumber(user.getNumber()); // Fixed incorrect password update

        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }


    public ResponseEntity<String> deleteUser(Long id) {
        Optional<User> userExist = userRepository.findById(id);
        if (userExist.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        userRepository.delete(userExist.get());
        return ResponseEntity.ok("User deleted successfully");
    }
}
