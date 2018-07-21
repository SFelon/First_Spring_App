package com.sebastianfelon.myfirstspringapp.controller;

import com.sebastianfelon.myfirstspringapp.exception.ResourceNotFoundException;
import com.sebastianfelon.myfirstspringapp.model.CarOwner;
import com.sebastianfelon.myfirstspringapp.repository.CarOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CarOwnerController {

    @Autowired
    CarOwnerRepository carOwnerRepository;

    @GetMapping("/car_owners")
    public Page<CarOwner> getAllCarOwners(Pageable pageable) {
        return carOwnerRepository.findAll(pageable);
    }

    @PostMapping("/car_owners")
    public CarOwner addCarOwner(@Valid @RequestBody CarOwner carOwner) {
        return carOwnerRepository.save(carOwner);
    }

    @PutMapping("/car_owners/{carOwnerId}")
    public CarOwner updateCarOwnerDetails(@PathVariable Long carOwnerId,
                                          @Valid @RequestBody CarOwner carOwnerRequest) {
        return carOwnerRepository.findById(carOwnerId).map(carOwner -> {
            carOwner.setName(carOwnerRequest.getName());
            return carOwnerRepository.save(carOwner);
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found."));
    }

    @DeleteMapping("/car_owners/{carOwnerId}")
    public ResponseEntity<?> deleteCarOwner(@PathVariable (value = "carOwnerId") Long carOwnerId) {
        return carOwnerRepository.findById(carOwnerId).map(carOwner -> {
            carOwnerRepository.delete(carOwner);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Car Owner Id " + carOwnerId + " not found."));
    }
}
