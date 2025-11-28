package com.example.TestDemoApplication.Controller;

import com.example.TestDemoApplication.DTO.UserRegisterDTO;
import com.example.TestDemoApplication.Entity.User;
import com.example.TestDemoApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/userValidation")
    public String userValidation(@RequestParam String userId, @RequestParam String userPassword){
        boolean isValid= userService.userValidation(userId, userPassword);
        return isValid ? "User verified":"Invalid User";
    }
    @PostMapping("/userRegistration")
    public String userRegistration(@RequestBody UserRegisterDTO dto){
        String result= userService.userRegistry(dto);
        return result;
    }
}
