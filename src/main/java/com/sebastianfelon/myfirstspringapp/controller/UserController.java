package com.sebastianfelon.myfirstspringapp.controller;

import com.sebastianfelon.myfirstspringapp.exception.ResourceNotFoundException;
import com.sebastianfelon.myfirstspringapp.model.User;
import com.sebastianfelon.myfirstspringapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{userId}")
    public User updateCarOwnerDetails(@PathVariable Long userId,
                                      @Valid @RequestBody User userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setName(userRequest.getName());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable (value = "userId") Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }
}
