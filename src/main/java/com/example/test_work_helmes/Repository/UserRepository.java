package com.example.test_work_helmes.Repository;

import com.example.test_work_helmes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}

