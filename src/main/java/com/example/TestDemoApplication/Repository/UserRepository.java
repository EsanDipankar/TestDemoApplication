package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAuth, String> {
    UserAuth findByuserId(String userId);
}
