package com.example.TestDemoApplication.Service.User;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.User.UserResetPasswordDto;
import com.example.TestDemoApplication.DTO.UserRegisterDTO;
import com.example.TestDemoApplication.Entity.UserAuth;
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
        UserAuth userAuth = userRepository.findByuserId(userId);
        if(userAuth != null) return false;
        else{
        return passwordEncoder.matches(rawPassword, userAuth.getPassword());
        }
    }
//    public String userRegistry(UserRegisterDTO dto){ // Without Encryption
//        UserAuth existingUserAuth = userRepository.findByuserId(dto.getUserId());
//        if(existingUserAuth != null){
//        return "User already exist";
//        }
//        UserAuth userAuth = new UserAuth();
//        userAuth.setUserId(dto.getUserId());
//        userAuth.setUserName(dto.getUserName());
//        userAuth.setRole(dto.getRole());
//        userAuth.setEmail(dto.getEmail());
//        String encryptedpassword= passwordEncoder.encode(dto.getPassword());
//        userAuth.setPassword(encryptedpassword);
//        userRepository.save(userAuth);
//        return "User Registered Successfully";
//
//    }
public String userRegistry(UserRegisterDTO dto){
    try {

        // Decrypt incoming values
        String userId = AESUtil.decrypt(dto.getUserId());
        String userName = AESUtil.decrypt(dto.getUserName());
        String email = AESUtil.decrypt(dto.getEmail());
        String role = AESUtil.decrypt(dto.getRole());
        String password = AESUtil.decrypt(dto.getPassword());

        UserAuth existingUserAuth = userRepository.findByuserId(userId);
        if(existingUserAuth != null){
            return "User already exist";
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(userId);
        userAuth.setUserName(userName);
        userAuth.setRole(role);
        userAuth.setEmail(email);

        // Password hashing (not encryption)
        userAuth.setPassword(passwordEncoder.encode(password));

        userRepository.save(userAuth);

        return "User Registered Successfully";

    } catch (Exception e) {

        System.out.println(dto.getUserId());
        return dto.getEmail();
    }
}
    public String userResetPassword(UserResetPasswordDto userResetPasswordDto){
        UserAuth userAuth = userRepository.findByuserId(userResetPasswordDto.getUserId());
        if(userAuth == null){
            return "User not exist";
        }
        String encryptedPassword = passwordEncoder.encode(userResetPasswordDto.getOldPassword());

        if(! passwordEncoder.matches(userResetPasswordDto.getOldPassword(), userAuth.getPassword())){
            return "Password Missmatched";
        }
        if(! userResetPasswordDto.getOldPassword().equals(userResetPasswordDto.getNewCnfPassword())){
            return "New Password and Confirm Password is not same";
        }
        if (!userResetPasswordDto.getNewPassword().equals(userResetPasswordDto.getNewCnfPassword())) {
            return "New password and Confirm password do not match";
        }
        if (passwordEncoder.matches(userResetPasswordDto.getNewPassword(), userAuth.getPassword())) {
            return "New password cannot be same as old password";
        }

        String encryptedPasswoed = passwordEncoder.encode(userResetPasswordDto.getNewPassword());
        userAuth.setPassword(encryptedPasswoed);
        userRepository.save(userAuth);
        return "Password updated successfully";
    }


}
