package org.example.learningprojectserver.controller;


import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Learning-App/")
public class userController {
@Autowired
private UserService userService;


    @PostMapping("/add-user/")
    public RegisterResponse registerUser(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String confirmPassword,
                                         @RequestParam String email,
                                         @RequestParam String phone) {

        return userService.createUser(
                username,
                password,
                confirmPassword,
                email,
                phone
        );
    }
    @PostMapping("/login")
    public BasicResponse loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
    @PostMapping("/send-otp")
    public BasicResponse sendOtp(@RequestParam String username, @RequestParam String phoneNumber) {
        return userService.sendOtp(username, phoneNumber);
    }
    @PostMapping("/verify-otp")
    public LoginResponse verifyOtp(@RequestParam String username, @RequestParam String otp) {
        return userService.verifyOtp(username, otp);
    }
    @PostMapping("/forgot-password")
    public ResetPasswordResponse forgotPassword(@RequestParam String username) {
        return userService.sendPasswordResetOtp(username);
    }

    @PostMapping("/reset-password")
    public ResetPasswordResponse resetPassword(@RequestParam String username,
                                       @RequestParam String otp,
                                       @RequestParam String newPassword) {
        return userService.resetPassword(username, otp, newPassword);
    }



}