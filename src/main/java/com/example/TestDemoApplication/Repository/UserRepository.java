package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByuserId(String userId);
}
