package org.example.learningprojectserver.jobs;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.example.learningprojectserver.utils.ApiEmailProcessor.sendEmail;

@Component
public class InactiveUsersJob {
    private final UserRepository userRepository;


    @Autowired
    public InactiveUsersJob(UserRepository userRepository){
        this.userRepository = userRepository;
    }
//    @PostConstruct
//    public void init(){
//        sendEmail("shaiberkovich@edu.aac.ac.il",TITLE,CONTENT);
//        sendEmail("shai27133@gmail.com",TITLE,CONTENT);
//    }


    private static final String CONTENT = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title> חזרתך חשובה לנו!</title></head><body style=\"margin: 0; padding: 0; background-color: #f4f4f4;\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#6a11cb\"><tr><td align=\"center\"><table width=\"600\" cellspacing=\"0\" cellpadding=\"20\" bgcolor=\"#ffffff\" style=\"border-radius: 15px; margin: 20px; text-align: center; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);\"><tr><td><h1 style=\"color:#2c3e50; font-size: 32px; font-weight: bold; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); margin-bottom: 20px;\"> חזרתך חשובה לנו!</h1><p style=\"color:#333; font-size: 20px; font-weight: 500; line-height: 1.6; background-color: #ffecb3; padding: 10px 20px; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);\">אל תפספס את ההזדמנות להשתפר </p><hr style=\"border: 1px solid #ddd; margin: 20px 0;\"><h2 style=\"color:#ff5722; font-size: 24px;\"> לא ראינו אותך כבר שבוע!</h2><p style=\"color:#333; font-size: 16px;\">אנחנו מתגעגעים אליך! </p><p style=\"color:#555; font-size: 16px;\">השבוע היה מלא בתרגולים מרתקים ואתגרים חדשים,<br> אבל אתה עדיין יכול להצטרף ולהתקדם!</p><a href=\"https://yourwebsite.com/login\" style=\"display:inline-block;background:#ff7f50;color:black;padding:14px 30px;text-decoration:none;border-radius:50px;font-weight:bold;font-size:18px;margin-top:20px;\">✨ חזור עכשיו ללמידה</a><hr style=\"border: 1px solid #ddd; margin: 20px 0;\"><p style=\"color:#777; font-size: 14px;\">תראה איך הלמידה שלך משתפרת, ואל תפספס את ההזדמנות להצטיין! </p></td></tr></table></td></tr></table></body></html>";


    private static final String TITLE = "🔥 חזרתך חשובה לנו! אל תפספס את ההזדמנות להשתפר 🚀";


//    private static final String CONTENT = """
//        <!DOCTYPE html>
//               <html>
//               <head>
//                   <meta charset="UTF-8">
//                   <title>💡 חזרתך חשובה לנו!</title>
//               </head>
//               <body style="margin: 0; padding: 0; background-color: #f4f4f4;">
//                   <table width="100%" cellspacing="0" cellpadding="0" bgcolor="#6a11cb">
//                       <tr>
//                           <td align="center">
//                               <table width="600" cellspacing="0" cellpadding="20" bgcolor="#ffffff" style="border-radius: 15px; margin: 20px; text-align: center; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);">
//                                   <tr>
//                                       <td>
//                                          <h1 style="color:#2c3e50; font-size: 32px; font-weight: bold; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); margin-bottom: 20px;">
//                                                          🚀 חזרתך חשובה לנו!
//                                                      </h1>
//                                                      <p style="color:#333; font-size: 20px; font-weight: 500; line-height: 1.6; background-color: #ffecb3; padding: 10px 20px; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);">
//                                                          אל תפספס את ההזדמנות להשתפר 🔥
//                                                      </p>
//
//                                           <hr style="border: 1px solid #ddd; margin: 20px 0;">
//                                           <h2 style="color:#ff5722; font-size: 24px;">🔥 לא ראינו אותך כבר שבוע!</h2>
//                                           <p style="color:#333; font-size: 16px;">אנחנו מתגעגעים אליך! 📚</p>
//                                           <p style="color:#555; font-size: 16px;">
//                                               השבוע היה מלא בתרגולים מרתקים ואתגרים חדשים,<br> אבל אתה עדיין יכול להצטרף ולהתקדם!
//                                           </p>
//                                           <a href="https://yourwebsite.com/login" style="display:inline-block;background:#ff7f50;color:black;padding:14px 30px;text-decoration:none;border-radius:50px;font-weight:bold;font-size:18px;margin-top:20px;">
//                                               ✨ חזור עכשיו ללמידה
//                                           </a>
//                                           <hr style="border: 1px solid #ddd; margin: 20px 0;">
//                                           <p style="color:#777; font-size: 14px;">תראה איך הלמידה שלך משתפרת, ואל תפספס את ההזדמנות להצטיין! 🚀</p>
//                                       </td>
//                                   </tr>
//                               </table>
//                           </td>
//                       </tr>
//                   </table>
//               </body>
//               </html>
//""";









//    private static final String CONTENT = """
//            <!DOCTYPE html>
//                   <html>
//                   <head>
//                       <meta charset="UTF-8">
//                       <title>💡 חזרתך חשובה לנו!</title>
//                   </head>
//                   <body style="margin: 0; padding: 0; background-color: #f4f4f4;">
//                       <table width="100%" cellspacing="0" cellpadding="0" bgcolor="#6a11cb">
//                           <tr>
//                               <td align="center">
//                                   <table width="600" cellspacing="0" cellpadding="20" bgcolor="#ffffff" style="border-radius: 15px; margin: 20px; text-align: center; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);">
//                                       <tr>
//                                           <td>
//                                               <h1 style="color:#2c3e50; font-size: 28px;">🚀 חזרתך חשובה לנו!</h1>
//                                               <p style="color:#555; font-size: 18px;">אל תפספס את ההזדמנות להשתפר 🔥</p>
//                                               <hr style="border: 1px solid #ddd; margin: 20px 0;">
//                                               <h2 style="color:#ff5722; font-size: 24px;">🔥 לא ראינו אותך כבר שבוע!</h2>
//                                               <p style="color:#333; font-size: 16px;">אנחנו מתגעגעים אליך! 📚</p>
//                                               <p style="color:#555; font-size: 16px;">
//                                                   השבוע היה מלא בתרגולים מרתקים ואתגרים חדשים,<br> אבל אתה עדיין יכול להצטרף ולהתקדם!
//                                               </p>
//                                               <a href="https://yourwebsite.com/login" style="display:inline-block;background:#ff758c;color:white;padding:14px 30px;text-decoration:none;border-radius:50px;font-weight:bold;font-size:18px;margin-top:20px;">
//                                                   ✨ חזור עכשיו ללמידה
//                                               </a>
//                                               <hr style="border: 1px solid #ddd; margin: 20px 0;">
//                                               <p style="color:#777; font-size: 14px;">תראה איך הלמידה שלך משתפרת, ואל תפספס את ההזדמנות להצטיין! 🚀</p>
//                                           </td>
//                                       </tr>
//                                   </table>
//                               </td>
//                           </tr>
//                       </table>
//                   </body>
//                   </html>
//
//""";
//

    @Scheduled(cron = "0 * 12 * * *")
    public void sendMailToNotLoggedUsers(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeek = calendar.getTime();
        List<UserEntity> loginActivityList = userRepository.findUsersNotLoggedInLastWeek(lastWeek);
        if (loginActivityList != null){
            List<String> emails = loginActivityList.stream().map(UserEntity::getEmail).toList();
            for (String email : emails){

                sendEmail(email,TITLE ,CONTENT);
            }
        }
    }
}