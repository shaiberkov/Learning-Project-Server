package org.example.learningprojectserver.service;

import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.repository.SessionRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.learningprojectserver.utils.ApiEmailProcessor.sendEmail;
import static org.example.learningprojectserver.utils.GeneratorUtils.*;
import static org.example.learningprojectserver.utils.SmsSender.sendSms;

@Service
public class UserService {

@Autowired
    private UserRepository userRepository;

@Autowired
private JwtService jwtService;

    @Autowired
    private SessionRepository sessionRepository;
    private static final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
 public RegisterResponse createUser(String username,String userId, String password, String confirmPassword, String email, String phone) {
//todo להוסיף פונקציה שבודקת תקינות סיסמא
     RegisterResponse registerResponse=new RegisterResponse();
     if (username == null || userId==null || password == null || confirmPassword == null || email == null || phone == null) {
         registerResponse.setMessage("השדות לא מלאים");
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     if (!password.equals(confirmPassword)) {
         registerResponse.setPasswordDontMatch("password doesnt match");
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     if (!isValid(userId, phone, email, registerResponse)) {
         registerResponse.setSuccess(false);
         return registerResponse;
     }

     try {


         String salt = generateSalt();
         String hashedPassword = hashPassword(password, salt);

         StudentEntity newUser = new StudentEntity();
         newUser.setRole(Role.STUDENT);
         newUser.setUserId(userId);
         newUser.setUsername(username);
         newUser.setEmail(email);
         newUser.setPhoneNumber(phone);
         newUser.setSalt(salt);
         newUser.setPasswordHash(hashedPassword);

         QuestionHistoryEntity questionHistoryEntity = new QuestionHistoryEntity();
         UserProgressEntity userProgressEntity = new UserProgressEntity();

         questionHistoryEntity.setStudent(newUser);
         userProgressEntity.setStudent(newUser);

         newUser.setStudentProgressEntity(userProgressEntity);
         newUser.setQuestionHistoryEntity(questionHistoryEntity);

         String textToSendInMailToUser = generateMailText(username);
         sendEmail(email, "ברוך הבא ל-'מרכז תרגול אישי' – החשבון שלך נוצר בהצלחה!", textToSendInMailToUser);

         userRepository.save(newUser);
         System.out.println(newUser);


         registerResponse.setMessage("User successfully registered");
         registerResponse.setSuccess(true);
         return registerResponse;
     } catch (Exception e) {
         registerResponse.setMessage("Error occurred during registration");
         registerResponse.setSuccess(false);
         e.printStackTrace();
         return registerResponse;
     }
 }
    private boolean isValid(String userId, String phoneNumber, String email, RegisterResponse registerResponse) {
        boolean isValid = true;
         UserEntity user= userRepository.findUserByUserId(userId);
        if (user!=null){
           registerResponse.setIdTaken("תעודת זהות זו כבר קיימת במערכת");
            isValid = false;
        }
//        if (!isValidIsraeliId(userId)){
//            registerResponse.setIdTaken("תעודת זהות זו לא תקינה");
//            isValid = false;
//        }
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
        if (!isValidEmail(email)){
            registerResponse.setEmailTaken("Email is not valid");

            isValid = false;
        }

        return isValid;
    }

    public BasicResponse loginUser(String userId, String password) {
        BasicResponse basicResponse = new BasicResponse();

        if (userId == null || password == null) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("username or password is required");
            return basicResponse;
        }

        try {
            UserEntity userEntity= userRepository.findUserByUserId(userId);

            if (userEntity == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("password/username not found");
                return basicResponse;
            }
            String hashedPassword = hashPassword(password, userEntity.getSalt());
            if (!hashedPassword.equals(userEntity.getPasswordHash())) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("Password isnt correct");
                return basicResponse;
            }

            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            return basicResponse;

        } catch (Exception e) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Error occurred during login");
            return basicResponse;
        }
    }
    public BasicResponse sendOtp(String userId, String phoneNumber) {
        BasicResponse basicResponse = new BasicResponse();

        if (phoneNumber == null) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Phone number is required");
            return basicResponse;
        }

        try {
            UserEntity userEntity= userRepository.findUserByUserId(userId);
            if (userEntity == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("User not found");
                return basicResponse;
            }
            String otp = generatorCode();
            List<String> toSend = new ArrayList<>();
            toSend.add(phoneNumber);
            System.out.println(phoneNumber);
            userEntity.setOtp(otp);
            userEntity.setOtpTimestamp(System.currentTimeMillis());
            userRepository.save(userEntity);

            sendSms(otp, toSend);

            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("OTP sent");
            return basicResponse;

        } catch (Exception e) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("Error occurred during send otp");
            return basicResponse;
        }
    }




        public LoginResponse verifyOtp(String userId, String otp) {
            LoginResponse loginResponse = new LoginResponse();
            if (userId == null || otp == null) {
                loginResponse.setSuccess(false);
                loginResponse.setErrorCode("username or otp is required");
                return loginResponse;
            }
            try {
                UserEntity userEntity= userRepository.findUserByUserId(userId);


                if (userEntity == null) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("User not found");

                    return loginResponse;
                }

                if (userEntity.getOtp() == null || !userEntity.getOtp().equals(otp)) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("verfication code does not match");
                    return loginResponse;
                }

                long otpTimestamp = userEntity.getOtpTimestamp();
                long currentTime = System.currentTimeMillis();
                if (currentTime - otpTimestamp > 120000) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("2 minutes passed");
                    return loginResponse;
                }
                String token = jwtService.generateToken(userId,String.valueOf(userEntity.getRole()));
                loginResponse.setSuccess(true);
                loginResponse.setErrorCode("code is correct");
                loginResponse.setToken(token);
                userEntity.setOtp(null);
                userEntity.setOtpTimestamp(null);
                Session session = new Session();
                session.setUser(userEntity);
                session.setLastActivity(new Date());

                userEntity.getSessionList().add(session);

                sessionRepository.save(session);
                userRepository.save(userEntity);
                return loginResponse;

            } catch (Exception e) {

                loginResponse.setSuccess(false);
                loginResponse.setErrorCode("Error occurred during verify otp");
                return loginResponse;
            }
        }
    public ResetPasswordResponse sendPasswordResetOtp(String userId) {
        ResetPasswordResponse resetPasswordResponse= new ResetPasswordResponse();
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserIdError("משתמש לא נמצא");
            return resetPasswordResponse;
        }

        String otp = generatorCode();
        otpStorage.put(userId, otp);
        System.out.println(userId+      "111111"+otpStorage.get(userId));

        sendSms( otp,  List.of(user.getPhoneNumber()) );
            resetPasswordResponse.setSuccess(true);
        return resetPasswordResponse;
    }

    public ResetPasswordResponse resetPassword(String userId, String otp, String newPassword) {
       ResetPasswordResponse resetPasswordResponse= new ResetPasswordResponse();
        String subject = "הסיסמה שלך שונתה בהצלחה";
        String message = "שלום,\n\nסיסמתך שונתה בהצלחה. אם לא ביצעת שינוי זה, אנא צור קשר עם התמיכה שלנו באופן מיידי.\n\nבברכה,\nצוות התמיכה";
        System.out.println(userId+      "22222"+otpStorage.get(userId));

        if(!otpStorage.containsKey(userId)){
            System.out.println(userId+      "33333"+otpStorage.get(userId));

             resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserIdError("תז לא תקין");
            return resetPasswordResponse;

        }
        if (!otpStorage.get(userId).equals(otp)) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setOtpError("קוד האימות שגוי או שפג תוקפו.");
            return resetPasswordResponse;
        }

        if(!isPasswordStrong(newPassword)){
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setPasswordError("הסיסמא חייבת לכלול לפחות אות גדולה וקטנה, ו-6 תווים לפחות");
            return resetPasswordResponse;
        }

        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserIdError("תז לא נמצא.");
            return resetPasswordResponse;
        }
        String userSalt = user.getSalt();
        String hashPassword=hashPassword(newPassword,userSalt);
        user.setPasswordHash(hashPassword);
        userRepository.save(user);
        otpStorage.remove(userId);
        sendEmail(user.getEmail(), subject, message);
        resetPasswordResponse.setSuccess(true);
        return resetPasswordResponse;
    }


    public UserDto getUserPhoneNumber(String userId) {
        UserDto userDto=new UserDto();
        String userPhoneNumber= userRepository.findPhoneNumberByUsername(userId);
        userDto.setPhoneNumber(userPhoneNumber);
        return userDto;
    }
}



