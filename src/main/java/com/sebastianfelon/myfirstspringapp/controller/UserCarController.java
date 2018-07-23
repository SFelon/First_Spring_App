package com.sebastianfelon.myfirstspringapp.controller;

import com.sebastianfelon.myfirstspringapp.exception.ResourceNotFoundException;
import com.sebastianfelon.myfirstspringapp.model.User;
import com.sebastianfelon.myfirstspringapp.repository.CarRepository;
import com.sebastianfelon.myfirstspringapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/users/{userId}/cars/{carId}")
    public User createLink(@PathVariable (value = "userId") Long userId,
                              @PathVariable (value = "carId") Long carId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User Id " + userId + " not found.");
        } else if (!carRepository.existsById(carId)){
            throw new ResourceNotFoundException("Car Id " + carId + " not found.");
        }
        return userRepository.findById(userId).map(user -> {
            user.getCars().add(carRepository.findById(carId).get());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public User deleteCar(@PathVariable (value = "userId") Long userId,
                          @PathVariable (value = "carId") Long carId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User Id " + userId + " not found.");
        } else if (!carRepository.existsById(carId)){
            throw new ResourceNotFoundException("Car Id " + carId + " not found.");
        }

        return userRepository.findById(userId).map(user -> {
            user.getCars().remove(carRepository.getOne(carId));
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + userId + " not found."));
    }
}
