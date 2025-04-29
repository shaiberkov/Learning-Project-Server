package org.example.learningprojectserver.controller;


import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/User")
public class userController {
@Autowired
private UserService userService;


    @PostMapping("/add-user/")
    public RegisterResponse registerUser(@RequestParam String username,
                                         @RequestParam String userId,
                                         @RequestParam String password,
                                         @RequestParam String confirmPassword,
                                         @RequestParam String email,
                                         @RequestParam String phone) {

        return userService.createUser(
                username,
                userId,
                password,
                confirmPassword,
                email,
                phone
        );
    }
    @PostMapping("/login")
    public BasicResponse loginUser(@RequestParam String userId, @RequestParam String password) {
        return userService.loginUser(userId, password);
    }
    @PostMapping("/send-otp")
    public BasicResponse sendOtp(@RequestParam String userId, @RequestParam String phoneNumber) {
        return userService.sendOtp(userId, phoneNumber);
    }
    @PostMapping("/verify-otp")
    public LoginResponse verifyOtp(@RequestParam String userId, @RequestParam String otp) {
        return userService.verifyOtp(userId, otp);
    }

    @PostMapping("/forgot-password")
    public ResetPasswordResponse forgotPassword(@RequestParam String userId) {
        return userService.sendPasswordResetOtp(userId);
    }

    @PostMapping("/reset-password")
    public ResetPasswordResponse resetPassword(@RequestParam String userId,
                                       @RequestParam String otp,
                                       @RequestParam String newPassword) {
        return userService.resetPassword(userId, otp, newPassword);
    }
    @GetMapping("/user/phone")
    public UserDto getUserPhoneNumber(@RequestParam String userId) {
        return userService.getUserPhoneNumber(userId);

    }



}