package com.sebastianfelon.myfirstspringapp.controller;


import com.sebastianfelon.myfirstspringapp.exception.ResourceNotFoundException;
import com.sebastianfelon.myfirstspringapp.model.Car;
import com.sebastianfelon.myfirstspringapp.repository.CarOwnerRepository;
import com.sebastianfelon.myfirstspringapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    @GetMapping("/car_owners/{carOwnerId}/cars")
    public Page<Car> getAllCarsByCarOwnerId(@PathVariable (value = "carOwnerId") Long carOwnerId,  Pageable pageable) {
        return carRepository.findByCarOwnerId(carOwnerId, pageable);
    }

    @PostMapping("/car_owners/{carOwnerId}/cars")
    public Car createCar(@PathVariable (value = "carOwnerId") Long carOwnerId,
                         @Valid @RequestBody Car car) {
        return carOwnerRepository.findById(carOwnerId).map(carOwner -> {
            car.setCarOwner(carOwner);
            return carRepository.save(car);
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found."));
    }

    @PutMapping("/car_owners/{carOwnerId}/cars/{carId}")
    public Car updateCarModel(@PathVariable (value = "carOwnerId") Long carOwnerId,
                              @PathVariable (value = "carId") Long carId,
                              @Valid @RequestBody Car carRequest ) {
        if(!carOwnerRepository.existsById(carOwnerId)) {
            throw new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found.");
        }

        return carRepository.findById(carId).map(car -> {
            car.setCarModel(carRequest.getCarModel());
            return carRepository.save(car);
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found."));
    }

    @DeleteMapping("/car_owners/{carOwnerId}/cars/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable (value = "carOwnerId") Long carOwnerId,
                                       @PathVariable (value = "carId") Long carId) {
        if(!carOwnerRepository.existsById(carOwnerId)) {
            throw new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found.");
        }

        return carRepository.findById(carId).map(car -> {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found."));
    }
}
