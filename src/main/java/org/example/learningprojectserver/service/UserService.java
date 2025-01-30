package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.utils.ApiEmailProcessor;
import org.example.learningprojectserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.learningprojectserver.utils.ApiEmailProcessor.sendEmail;
import static org.example.learningprojectserver.utils.GeneratorUtils.*;
import static org.example.learningprojectserver.utils.SmsSender.sendSms;

@Service
public class UserService {

@Autowired
    private UserRepository userRepository;
    private static final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
 public RegisterResponse createUser(String username, String password, String confirmPassword, String email, String phone) {
//todo להוסיף פונקציה שבודקת תקינות סיסמא
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
         String textToSendInMailToUser =generateMailText(username);
         sendEmail(email,"ברוך הבא ל-'מרכז תרגול אישי' – החשבון שלך נוצר בהצלחה!",textToSendInMailToUser);
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
        if (!isValidEmail(email)){
            registerResponse.setEmailTaken("Email is not valid");

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




        public LoginResponse verifyOtp(String username, String otp) {
            LoginResponse loginResponse = new LoginResponse();
            if (username == null || otp == null) {
                loginResponse.setSuccess(false);
                loginResponse.setErrorCode("username or otp is required");
                return loginResponse;
            }
            try {
                UserEntity userEntity = userRepository.findByUsername(username);

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
                String token = JwtUtils.generateToken(username);
                loginResponse.setSuccess(true);
                loginResponse.setErrorCode("code is correct");
                loginResponse.setToken(token);
                userEntity.setOtp(null);
                userEntity.setOtpTimestamp(null);

                userRepository.save(userEntity);
                return loginResponse;

            } catch (Exception e) {
                e.printStackTrace();
                loginResponse.setSuccess(false);
                loginResponse.setErrorCode("Error occurred during verify otp");
                return loginResponse;
            }
        }
    public ResetPasswordResponse sendPasswordResetOtp(String username) {
        ResetPasswordResponse resetPasswordResponse= new ResetPasswordResponse();
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserNameError("User not found");
            return resetPasswordResponse;
        }

        String otp = generatorCode();
        otpStorage.put(username, otp); // שמירת OTP זמנית
        sendSms( otp,  List.of(user.getPhoneNumber()) ); // שליחת OTP לטלפון
            resetPasswordResponse.setSuccess(true);
        return resetPasswordResponse;
    }

    public ResetPasswordResponse resetPassword(String username, String otp, String newPassword) {
       ResetPasswordResponse resetPasswordResponse= new ResetPasswordResponse();
        String subject = "הסיסמה שלך שונתה בהצלחה";
        String message = "שלום,\n\nסיסמתך שונתה בהצלחה. אם לא ביצעת שינוי זה, אנא צור קשר עם התמיכה שלנו באופן מיידי.\n\nבברכה,\nצוות התמיכה";
        if(!otpStorage.containsKey(username)){

             resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserNameError("שם משתמש לא תקין");
            return resetPasswordResponse;

        }
        if (!otpStorage.get(username).equals(otp)) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setOtpError("קוד האימות שגוי או שפג תוקפו.");
            return resetPasswordResponse;
        }

        if(!isPasswordStrong(newPassword)){
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setPasswordError("הסיסמא חייבת לכלול לפחות אות גדולה וקטנה, ו-6 תווים לפחות");
            return resetPasswordResponse;
        }

        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            resetPasswordResponse.setSuccess(false);
            resetPasswordResponse.setUserNameError("שם משתמש לא נמצא.");
            return resetPasswordResponse;
        }
        String userSalt = user.getSalt();
        String hashPassword=hashPassword(newPassword,userSalt);
        user.setPasswordHash(hashPassword);
        userRepository.save(user);
        otpStorage.remove(username);
        sendEmail(user.getEmail(), subject, message);
        resetPasswordResponse.setSuccess(true);
        return resetPasswordResponse;
    }

    private String generateMailText(String username) {
        return """
        <html>
        <body>
            <h2>ברוך הבא ל-"מרכז תרגול אישי"!</h2>
            <p>שלום %s,</p>
            <p>אנו שמחים שבחרת להצטרף אלינו!</p>
            <p>בפלטפורמה שלנו תוכל לשפר את הידע שלך, לתרגל נושאים שונים ולעקוב אחרי ההתקדמות שלך.</p>
            
            <h3>איך להתחיל?</h3>
            <ul>
                <li>היכנס לחשבון שלך והשלם את פרופיל המשתמש.</li>
                <li>בחר את הנושא שתרצה לתרגל.</li>
                <li>עקוב אחר ההתקדמות שלך ושפר את הביצועים!</li>
            </ul>

            <p>אם יש לך שאלות או בעיות, אל תהסס לפנות אלינו.</p>
            <p>צוות <strong>מרכז תרגול אישי</strong></p>

            <p style="color: gray; font-size: 12px;">אם לא נרשמת לאתר שלנו, ניתן להתעלם מהודעה זו.</p>
        </body>
        </html>
        """.formatted(username);
    }

}



