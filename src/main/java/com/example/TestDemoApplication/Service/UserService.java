package com.example.TestDemoApplication.Service;

import com.example.TestDemoApplication.DTO.UserRegisterDTO;
import com.example.TestDemoApplication.Entity.User;
import com.example.TestDemoApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public boolean userValidation(String userId, String rawPassword){
        User user= userRepository.findByuserId(userId);
        if(user!= null) return false;
        else{
        return passwordEncoder.matches(rawPassword, user.getPassword());
        }
    }
    public String userRegistry(UserRegisterDTO dto){
        User existingUser= userRepository.findByuserId(dto.getUserId());
        if(existingUser != null){
        return "User already exist";
        }
        User user= new User();
        user.setUserId(dto.getUserId());
        user.setUsName(dto.getUsName());
        user.setRole(dto.getRole());
        String encryptedpassword= passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedpassword);
        userRepository.save(user);
        return "User Registered Successfully";

    }

}
