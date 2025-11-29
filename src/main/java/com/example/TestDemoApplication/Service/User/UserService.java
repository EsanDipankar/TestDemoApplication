package com.example.TestDemoApplication.Service.User;

import com.example.TestDemoApplication.DTO.User.UserResetPasswordDto;
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
        user.setUsName(dto.getUserName());
        user.setRole(dto.getRole());
        String encryptedpassword= passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedpassword);
        userRepository.save(user);
        return "User Registered Successfully";

    }
    public String userResetPassword(UserResetPasswordDto userResetPasswordDto){
        User user= userRepository.findByuserId(userResetPasswordDto.getUserId());
        if(user == null){
            return "User not exist";
        }
        String encryptedPassword = passwordEncoder.encode(userResetPasswordDto.getOldPassword());

        if(! passwordEncoder.matches(userResetPasswordDto.getOldPassword(), user.getPassword())){
            return "Password Missmatched";
        }
        if(! userResetPasswordDto.getOldPassword().equals(userResetPasswordDto.getNewCnfPassword())){
            return "New Password and Confirm Password is not same";
        }
        if (!userResetPasswordDto.getNewPassword().equals(userResetPasswordDto.getNewCnfPassword())) {
            return "New password and Confirm password do not match";
        }
        String encryptedPasswoed = passwordEncoder.encode(userResetPasswordDto.getNewPassword());
        user.setPassword(encryptedPasswoed);
        userRepository.save(user);
        return "Password updated successfully";
    }


}
