package com.example.TestDemoApplication.Controller.User;

import com.example.TestDemoApplication.DTO.User.UserResetPasswordDto;
import com.example.TestDemoApplication.DTO.User.UserRegisterDTO;
import com.example.TestDemoApplication.Service.User.UserService;
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
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto){
        String result = userService.userResetPassword(userResetPasswordDto);
        return result;
    }
    @PostMapping("/addAddress")
    public String addAddress(){
        return "address";
    }
}
