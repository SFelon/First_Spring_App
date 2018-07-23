package com.sebastianfelon.myfirstspringapp.repository;

import com.sebastianfelon.myfirstspringapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
