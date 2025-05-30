package org.example.learningprojectserver.controller;


import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.learningprojectserver.constants.ControllerConstants.User.*;

@RestController
@RequestMapping(USER_BASE_PATH)
@RequiredArgsConstructor
public class userController {

private final UserService userService;


    @PostMapping(ADD_USER)
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
    @PostMapping(LOGIN)
    public BasicResponse loginUser(@RequestParam String userId, @RequestParam String password) {
        return userService.loginUser(userId, password);
    }
    @PostMapping(SEND_OTP)
    public BasicResponse sendOtp(@RequestParam String userId, @RequestParam String phoneNumber) {
        return userService.sendOtp(userId, phoneNumber);
    }
    @PostMapping(VERIFY_OTP)
    public LoginResponse verifyOtp(@RequestParam String userId, @RequestParam String otp) {
        return userService.verifyOtp(userId, otp);
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResetPasswordResponse forgotPassword(@RequestParam String userId) {
        return userService.sendPasswordResetOtp(userId);
    }

    @PostMapping(RESET_PASSWORD)
    public ResetPasswordResponse resetPassword(@RequestParam String userId,
                                       @RequestParam String otp,
                                       @RequestParam String newPassword) {
        return userService.resetPassword(userId, otp, newPassword);
    }
    @GetMapping(GET_USER_PHONE)
    public UserDto getUserPhoneNumber(@RequestParam String userId) {
        return userService.getUserPhoneNumber(userId);

    }



}