package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.LoginResponse;
import org.example.learningprojectserver.response.RegisterResponse;
import org.example.learningprojectserver.response.ResetPasswordResponse;
import org.example.learningprojectserver.utils.SmsSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.learningprojectserver.utils.ApiEmailProcessor.sendEmail;
import static org.example.learningprojectserver.utils.GeneratorUtils.*;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final ActiveUserService activeUserService;
    private final JwtService jwtService;
    private final SmsSender smsSender;

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
         registerResponse.setPasswordDontMatch("סיסמאות לא זהות");
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

         StudentQuestionHistoryEntity studentQuestionHistoryEntity = new StudentQuestionHistoryEntity();
         StudentProgressEntity studentProgressEntity = new StudentProgressEntity();

         studentQuestionHistoryEntity.setStudent(newUser);
         studentProgressEntity.setStudent(newUser);

         newUser.setStudentProgressEntity(studentProgressEntity);
         newUser.setQuestionHistoryEntity(studentQuestionHistoryEntity);

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
            registerResponse.setPhoneTaken("מיספר טלפון זה לא תקין");

            isValid = false;
        }
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            registerResponse.setPhoneTaken("טלפון זה תפוס");

        }
        if (userRepository.existsByEmail(email)){
            registerResponse.setEmailTaken("אימייל זה תפוס");

            isValid = false;
        }
        if (!isValidEmail(email)){
            registerResponse.setEmailTaken("אימייל לא תקין");

            isValid = false;
        }

        return isValid;
    }

//    @PostConstruct
//    public void init() {
//        userRepository.loadUserWithSessionsByUserId("325256017");
//    }

    public BasicResponse loginUser(String userId, String password) {
        BasicResponse basicResponse = new BasicResponse();

        if (userId == null || password == null) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("יש להזין שם משתמש וסיסמה");
            return basicResponse;
        }

        try {


            /////////
            UserEntity userEntity= userRepository.findUserByUserId(userId);
////////////

            if (userEntity == null) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("שם משתמש או סיסמה אינם נכונים");
                return basicResponse;
            }
            String hashedPassword = hashPassword(password, userEntity.getSalt());
            if (!hashedPassword.equals(userEntity.getPasswordHash())) {
                basicResponse.setSuccess(false);
                basicResponse.setErrorCode("הסיסמה שגויה");
                return basicResponse;
            }

            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            return basicResponse;

        } catch (Exception e) {
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("אירעה שגיאה במהלך ההתחברות");
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
            userEntity.setOtp(otp);
            userEntity.setOtpTimestamp(System.currentTimeMillis());
            userRepository.save(userEntity);

            System.out.println(otp);
           smsSender.sendSms(otp, toSend);

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
                loginResponse.setErrorCode("יש לספק שם משתמש וקוד אימות");
                return loginResponse;
            }
            try {
               UserEntity userEntity= userRepository.findUserByUserId(userId);
                //UserEntity userEntity= userRepository.loadUserWithSessionsByUserId(userId);

                if (userEntity == null) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("המשתמש לא נמצא");

                    return loginResponse;
                }

                if (userEntity.getOtp() == null || !userEntity.getOtp().equals(otp)) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("קוד האימות שגוי");
                    return loginResponse;
                }

                long otpTimestamp = userEntity.getOtpTimestamp();
                long currentTime = System.currentTimeMillis();
                if (currentTime - otpTimestamp > 120000) {
                    loginResponse.setSuccess(false);
                    loginResponse.setErrorCode("עברו 2 דקות");
                    return loginResponse;
                }
                String token = jwtService.generateToken(userId,userEntity.getUsername(),String.valueOf(userEntity.getRole()));
                loginResponse.setSuccess(true);
                loginResponse.setErrorCode("הקוד נכון");
                loginResponse.setToken(token);
                userEntity.setOtp(null);
                userEntity.setOtpTimestamp(null);
                Session session = new Session();
                session.setUser(userEntity);
                session.setLastActivity(new Date());
                activeUserService.connectUser(userId);
                userEntity.getSessionList().add(session);

                userRepository.save(userEntity);
                return loginResponse;

            } catch (Exception e) {

                loginResponse.setSuccess(false);
                loginResponse.setErrorCode("אירעה שגיאה במהלך אימות הקוד");
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

       smsSender.sendSms( otp,  List.of(user.getPhoneNumber()) );
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
        String userPhoneNumber= userRepository.findPhoneNumberByUserId(userId);
        userDto.setPhoneNumber(userPhoneNumber);
        return userDto;
    }


}



