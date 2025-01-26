package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.learningprojectserver.utils.GeneratorUtils.*;
import static org.example.learningprojectserver.utils.SmsSender.sendSms;

@Service
public class UserService {

@Autowired
    private UserRepository userRepository;

 public RegisterResponse createUser(String username, String password, String confirmPassword, String email, String phone) {

     RegisterResponse registerResponse=new RegisterResponse();
     if (username == null || password == null || confirmPassword == null || email == null || phone == null) {
         registerResponse.setMessage("All fields are required");
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     if (!password.equals(confirmPassword)) {
         registerResponse.setPasswordDontMatch("password doesnt match");
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     if (!isValid(username, phone, email, registerResponse)) {
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     try {

         String salt = generateSalt();
         String hashedPassword = hashPassword(password, salt);
         UserEntity newUser = new UserEntity();
         newUser.setUsername(username);
         newUser.setEmail(email);
         newUser.setPhoneNumber(phone);
         newUser.setSalt(salt);
         newUser.setPasswordHash(hashedPassword);
         userRepository.save(newUser);

         registerResponse.setMessage("User successfully registered");
         registerResponse.setSuccess(true);
         return registerResponse;
     } catch (Exception e) {
         e.printStackTrace();
         registerResponse.setMessage("Error occurred during registration");
         registerResponse.setSuccess(false);
         return registerResponse;
     }
 }
    private boolean isValid(String userName, String phoneNumber, String email, RegisterResponse registerResponse) {
        boolean isValid = true;
        if (userRepository.existsByUsername(userName)){
           registerResponse.setUsernameTaken("Username Taken");
            isValid = false;
        }
        if (!isValidPhoneNumber(phoneNumber)){
            registerResponse.setPhoneTaken("Phone number in not valid");

            isValid = false;
        }
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            registerResponse.setPhoneTaken("Phone Taken");

        }
        if (userRepository.existsByEmail(email)){
            registerResponse.setEmailTaken("Email Taken");

            isValid = false;
        }
        if (userRepository.existsByEmail(email)){
            registerResponse.setEmailTaken("Email Taken");

            isValid = false;
        }

        return isValid;
    }

    public BasicResponse loginUser(String username, String password) {//TODO סיסמא זהה אבל לא נותן לעשות לוגין
        BasicResponse basicResponse = new BasicResponse();

        if (username == null || password == null) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("username or password is required");
            return basicResponse;
        }

        try {
            UserEntity userEntity = userRepository.findByUsername(username);

            if (userEntity == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("User not found");
                return basicResponse;
            }
            String hashedPassword = hashPassword(password, userEntity.getSalt());
            System.out.println(hashedPassword);
            System.out.println(userEntity.getPasswordHash());
            if (!hashedPassword.equals(userEntity.getPasswordHash())) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("Password does not match");
                return basicResponse;
            }
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("User logged in");
            return basicResponse;

        } catch (Exception e) {
            e.printStackTrace();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Error occurred during login");
            return basicResponse;
        }
    }
    public BasicResponse sendOtp(String username, String phoneNumber) {
        BasicResponse basicResponse = new BasicResponse();

        if (phoneNumber == null) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Phone number is required");
            return basicResponse;
        }

        try {



            UserEntity userEntity = userRepository.findByUsername(username);

            if (userEntity == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("User not found");
                return basicResponse;
            }
            String otp = generatorCode();
            List<String> toSend = new ArrayList<>();
            toSend.add(phoneNumber);
            userEntity.setOtp(otp);
            userEntity.setOtpTimestamp(System.currentTimeMillis());
            userRepository.save(userEntity);

            sendSms(otp, toSend);

            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("OTP sent");
            return basicResponse;

        } catch (Exception e) {
            e.printStackTrace();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Error occurred during send otp");
            return basicResponse;
        }
    }




        public BasicResponse verifyOtp(String username, String otp) {
            BasicResponse basicResponse = new BasicResponse();

            if (username == null || otp == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("username or otp is required");

                return basicResponse;
            }

            try {
                UserEntity userEntity = userRepository.findByUsername(username);

                if (userEntity == null) {
                    basicResponse.setSuccess(false);
                    basicResponse.setErrorCode("User not found");

                    return basicResponse;
                }

                if (userEntity.getOtp() == null || !userEntity.getOtp().equals(otp)) {
                    basicResponse.setSuccess(false);
                    basicResponse.setErrorCode("verfication code does not match");
                    return basicResponse;
                }

                long otpTimestamp = userEntity.getOtpTimestamp();
                long currentTime = System.currentTimeMillis();
                if (currentTime - otpTimestamp > 120000) {
                    basicResponse.setSuccess(false);
                    basicResponse.setErrorCode("2 minutes passed");
                    return basicResponse;
                }

                basicResponse.setSuccess(true);
                basicResponse.setErrorCode("code is correct");
                userEntity.setOtp(null);
                userEntity.setOtpTimestamp(null);
                userRepository.save(userEntity);
                return basicResponse;

            } catch (Exception e) {
                e.printStackTrace();
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("Error occurred during verify otp");
                return basicResponse;
            }
        }
    }



