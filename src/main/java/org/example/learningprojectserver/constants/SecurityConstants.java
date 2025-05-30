package org.example.learningprojectserver.constants;

import java.util.List;

import static org.example.learningprojectserver.constants.ControllerConstants.ActiveUser.CONNECTED_USERS_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.ChatGpt.CHAT_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Lesson.LESSON_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Message.MESSAGE_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Notification.NOTIFICATION_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Question.QUESTION_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Schedule.SCHEDULE_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.SchoolManager.SCHOOL_MANAGER_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Session.SESSION_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Student.STUDENT_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.SystemAdmin.SYSTEM_ADMIN_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Teacher.TEACHER_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.Test.TEST_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.UpcomingEvents.UPCOMING_EVENTS_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.User.USER_BASE_PATH;
import static org.example.learningprojectserver.constants.ControllerConstants.UserStatistic.USER_STATISTIC_BASE_PATH;

public final class SecurityConstants {
    public static final class JwtAuthenticationFilterConstants {
        private JwtAuthenticationFilterConstants() {}

        public static final String ROLE_PREFIX  = "ROLE_";
        public static final String PRINCIPAL_USER_ID_KEY = "userId";
        public static final String PRINCIPAL_USERNAME_KEY = "username";
    }
    public static final class LoggingFilterConstants {
        private LoggingFilterConstants() {}

        public static final String UNKNOWN_USER_ID = "UNKNOWN";
        public static final String UNKNOWN_USERNAME = "UNKNOWN";
        public static final String UNKNOWN_ROLE = "UNKNOWN";
        public static final String JWT_DECODE_ERROR_PREFIX = "שגיאה בפענוח JWT: ";
        public static final String LOG_FORMAT =
                "[%s] %s %s -> %d | %s: %s | %s: %s | role: %s | ip: %s";
    }
    public static final class MetricsFilterConstants {
        private MetricsFilterConstants() {}
        public static final String PRINT_FORMAT =
                "Path: %s | Time: %dms | Total Requests: %d | Avg Response Time: %dms%n";
    }
    public final class SecurityMatcherConstants {

        private SecurityMatcherConstants() { }
        public static final String CORS_MAPPING_PATH = "/**";

        public static final String V3_API_DOCS               = "/v3/api-docs"         + CORS_MAPPING_PATH;
        public static final String SWAGGER_UI_RESOURCES      = "/swagger-ui"          + CORS_MAPPING_PATH;
        public static final String SWAGGER_UI_HTML           = "/swagger-ui.html";

        public static final String USER_ALL                  = USER_BASE_PATH         + CORS_MAPPING_PATH;
        public static final String VALIDATE_TOKEN_ALL        = SESSION_BASE_PATH      + CORS_MAPPING_PATH;

        public static final String SYSTEM_ADMIN_ALL          = SYSTEM_ADMIN_BASE_PATH + CORS_MAPPING_PATH;
        public static final String SCHOOL_MANAGER_ALL        = SCHOOL_MANAGER_BASE_PATH + CORS_MAPPING_PATH;
        public static final String TEACHER_ALL               = TEACHER_BASE_PATH      + CORS_MAPPING_PATH;
        public static final String STUDENT_ALL               = STUDENT_BASE_PATH      + CORS_MAPPING_PATH;

        public static final String ACTIVE_USER_ALL           = CONNECTED_USERS_BASE_PATH + CORS_MAPPING_PATH;
        public static final String CHAT_ALL                  = CHAT_BASE_PATH         + CORS_MAPPING_PATH;
        public static final String QUESTION_ALL              = QUESTION_BASE_PATH     + CORS_MAPPING_PATH;
        public static final String USER_STATISTIC_ALL        = USER_STATISTIC_BASE_PATH + CORS_MAPPING_PATH;
        public static final String TEST_ALL                  = TEST_BASE_PATH         + CORS_MAPPING_PATH;
        public static final String MESSAGE_ALL               = MESSAGE_BASE_PATH      + CORS_MAPPING_PATH;
        public static final String NOTIFICATION_ALL          = NOTIFICATION_BASE_PATH + CORS_MAPPING_PATH;
        public static final String LESSON_ALL                = LESSON_BASE_PATH       + CORS_MAPPING_PATH;
        public static final String SCHEDULE_ALL              = SCHEDULE_BASE_PATH     + CORS_MAPPING_PATH;
        public static final String UPCOMING_EVENTS_ALL       = UPCOMING_EVENTS_BASE_PATH + CORS_MAPPING_PATH;

        public static final String SYSTEM_ADMIN   = "SYSTEM_ADMIN";
        public static final String TEACHER        = "TEACHER";
        public static final String SCHOOL_MANAGER = "SCHOOLMANAGER";
        public static final String STUDENT        = "STUDENT";
        public static final List<String> ALLOWED_ORIGINS =
                List.of("http://localhost:5173");

        public static final List<String> ALLOWED_METHODS =
                List.of("GET", "POST", "PUT", "DELETE");

        public static final List<String> ALLOWED_HEADERS =
                List.of("*");
    }

    }


