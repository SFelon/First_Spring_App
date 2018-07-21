package com.sebastianfelon.myfirstspringapp.repository;

import com.sebastianfelon.myfirstspringapp.model.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner,Long> {
}
