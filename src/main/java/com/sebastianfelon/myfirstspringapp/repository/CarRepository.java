package com.sebastianfelon.myfirstspringapp.repository;


import com.sebastianfelon.myfirstspringapp.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    Page<Car> findByCarOwnerId(long carOwnerId, Pageable pageable);
}
