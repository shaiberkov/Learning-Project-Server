package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.dto.TeacherDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.*;
import org.example.learningprojectserver.notification.NotificationType;
import org.example.learningprojectserver.notification.dto.NewTestMessageDTO;
import org.example.learningprojectserver.notification.dto.NotificationDTO;
import org.example.learningprojectserver.notification.publisher.NotificationEventPublisher;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGeneratorFactory;
import org.example.learningprojectserver.service.QuestionGenerator.SubjectQuestionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class TeacherService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ClassRoomRepository classRoomRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final TeacherTestRepository teacherTestRepository;
    private final QuestionRepository questionRepository;
    private final QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper;
    private final LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper;
    private final QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper;
    private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
    private final TeacherEntityToTeacherDTOMapper teacherEntityToTeacherDTOMapper;
    private final SchoolRepository schoolRepository;
    private final LessonsToScheduleMapper lessonsToScheduleMapper;
    private final StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper;
    private final NotificationService notificationService;
    private final NotificationEventPublisher notificationEventPublisher;


    @Autowired
    public TeacherService(LessonRepository lessonRepository, UserRepository userRepository, ClassRoomRepository classRoomRepository, ScheduleRepository scheduleRepository, StudentRepository studentRepository, TeacherTestRepository teacherTestRepository, QuestionRepository questionRepository, QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper, LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper, QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper, TestEntityToTestDTOMapper testEntityToTestDTOMapper, TeacherEntityToTeacherDTOMapper teacherEntityToTeacherDTOMapper, SchoolRepository schoolRepository, LessonsToScheduleMapper lessonsToScheduleMapper, StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper, NotificationService notificationService, NotificationEventPublisher notificationEventPublisher) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.classRoomRepository = classRoomRepository;
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
        this.teacherTestRepository = teacherTestRepository;
        this.questionRepository = questionRepository;
        this.questionEntityToQuestionDTOMapper = questionEntityToQuestionDTOMapper;
        this.lessonEntityToLessonDTOMapper = lessonEntityToLessonDTOMapper;
        this.questionEntityToTestQuestionMapper = questionEntityToTestQuestionMapper;
        this.testEntityToTestDTOMapper = testEntityToTestDTOMapper;
        this.teacherEntityToTeacherDTOMapper = teacherEntityToTeacherDTOMapper;
        this.schoolRepository = schoolRepository;
        this.lessonsToScheduleMapper = lessonsToScheduleMapper;
        this.studentEntityToStudentTestStatusDTOMapper = studentEntityToStudentTestStatusDTOMapper;
        this.notificationService = notificationService;
        this.notificationEventPublisher = notificationEventPublisher;
    }


//    public BasicResponse getLessonsForTeacher(String teacherId) {
//
//        UserEntity teacher = userRepository.findUserByUserId(teacherId);
//        if (teacher == null) {
//
//            return new BasicResponse(false, "מורה לא נימצא");
//        }
//
//        List<LessonEntity> lessonEntities = lessonRepository.findLessonsByTeacherId(teacherId);
//
//        if (lessonEntities.isEmpty()) {
//            return new BasicResponse(false, "אין שיעורים למורה זה");
//        }
//
//
//        List<LessonDTO> lessonDTOs = lessonEntities.stream()
//                .map(lessonEntityToLessonDTOMapper).toList();
//
//        BasicResponse basicResponse = new BasicResponse(true, null);
//        basicResponse.setData(lessonDTOs);
//        return basicResponse;
//    }

    public BasicResponse getTeacherDTO(String teacherId,String schoolCode) {

        UserEntity user = userRepository.findUserByUserId(teacherId);
        SchoolEntity  school=schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false,"אין בית ספר כזה");
        }
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא במערכת");

        }
        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש אינו מורה");

        }
        if (!school.getTeachers().contains(user)) {
            return new BasicResponse(false, "המורה לא שייך לבית הספר ");

        }
        TeacherEntity teacher = (TeacherEntity) user;
        BasicResponse basicResponse = new BasicResponse(true, null);

        TeacherDTO teacherDTO = teacherEntityToTeacherDTOMapper.apply(teacher);
        basicResponse.setData(teacherDTO);
        return basicResponse;
    }



    public BasicResponse addTeachingSubjectToTeacher(String teacherId, List<String> subjects) {
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא במערכת");
        }
        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש אינו מורה");
        }
        TeacherEntity teacher = (TeacherEntity) user;

        List<String> subjectsOfTeacher=teacher.getTeachingSubjects();
        if (!Collections.disjoint(subjectsOfTeacher, subjects)) {
            return new BasicResponse(false,"לא ניתן להוסיף את המקצועות משום שחלק מהם כבר נמצאים ברשימה של המורה: " + String.join(", ", subjectsOfTeacher));
        }
        teacher.getTeachingSubjects().addAll(subjects);
        userRepository.save(teacher);

        return new BasicResponse(true, "המקצועות נוספו בהצלחה");
    }

    public BasicResponse removeTeachingSubjectFromTeacher(String teacherId,String subjectToRemove) {
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא במערכת");
        }
        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש אינו מורה");
        }
        TeacherEntity teacher = (TeacherEntity) user;
        if (!teacher.getTeachingSubjects().contains(subjectToRemove)) {
            return new BasicResponse(false, "המורה אינו מלמד מקצוע זה ");
        }

        teacher.getTeachingSubjects().remove(subjectToRemove);
        userRepository.save(teacher);

        return new BasicResponse(true, "המקצוע הוסר בהצלחה מהמורה.");
    }



    @Transactional
    public BasicResponse addLessonToTeacher(LessonDTO dto, String teacherId) {
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "מורה לא נמצא");
        }

        ClassRoomEntity classRoom = classRoomRepository.findClassRoomByName(dto.getClassRoomName());
        if (classRoom == null) {
            return new BasicResponse(false, "כיתה לא נמצאה");
        }

        TeacherEntity teacher = (TeacherEntity) user;
        if (!teacher.getTeachingClassRooms().contains(classRoom)) {
            return new BasicResponse(false, "המורה אינו משויך לכיתה '" + dto.getClassRoomName() + "'");
        }

        if (!teacher.getTeachingSubjects().contains(dto.getSubject())) {
            return new BasicResponse(false, "המורה אינו מוסמך ללמד את המקצוע '" + dto.getSubject() + "'");
        }

        DayOfWeek dayOfWeek = dto.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return new BasicResponse(false, "לא ניתן לשבץ לשיעור ביום שבת");
        }

        LocalTime startTime = LocalTime.parse(dto.getStartTime());
        LocalTime endTime = LocalTime.parse(dto.getEndTime());

        LessonEntity tempLesson = new LessonEntity();
        tempLesson.setDayOfWeek(dayOfWeek);
        tempLesson.setStartTime(startTime);
        tempLesson.setEndTime(endTime);

        for (LessonEntity existingLesson : teacher.getLessons()) {
            if (isOverlapping(tempLesson, existingLesson)) {
                return new BasicResponse(false, "קיים כבר שיעור אחר למורה בזמן זה");
            }
        }

        ScheduleEntity schedule = classRoom.getSchedule();
        if (schedule == null) {
            schedule = new ScheduleEntity();
            schedule.setClassRoom(classRoom);
            schedule = scheduleRepository.save(schedule);
            classRoom.setSchedule(schedule);
            classRoomRepository.save(classRoom);
        }

        for (LessonEntity existingLesson : schedule.getLessons()) {
            if (isOverlapping(tempLesson, existingLesson)) {
                return new BasicResponse(false, "קיים כבר שיעור אחר בכיתה בזמן זה");
            }
        }

        LessonEntity lesson = new LessonEntity();
        lesson.setSubject(dto.getSubject());
        lesson.setDayOfWeek(dayOfWeek);
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
        lesson.setTeacher(teacher);
        lesson.setSchedule(schedule);

        teacher.getLessons().add(lesson);
        lessonRepository.save(lesson);

        return new BasicResponse(true, "השיעור נוסף בהצלחה");
    }


    public boolean isOverlapping(LessonEntity newLesson, LessonEntity existingLesson) {
        if (newLesson.getDayOfWeek() != existingLesson.getDayOfWeek()) {
            return false;
        }

        LocalTime startTime = newLesson.getStartTime();
        LocalTime endTime = newLesson.getEndTime();
        LocalTime existingStart = existingLesson.getStartTime();
        LocalTime existingEnd = existingLesson.getEndTime();

        boolean isExactEdge = startTime.equals(existingEnd) || endTime.equals(existingStart);

        boolean overlaps = startTime.isBefore(existingEnd) && endTime.isAfter(existingStart);

        return overlaps && !isExactEdge;
    }




//    @PostConstruct()
//    public void init() {
//
//       BasicResponse basicResponse= generateTestForStudents(List.of("325256020"),"325256022","10:30","מתמטיקה","מספרים שלמים","קל",5,30);
//
//    }

    @Transactional
    public BasicResponse generateTestForStudents(List<String> usersIds, String teacherId, String testStartTime,
                                                 String subject, String topic, String difficulty, int questionCount, int timeLimitMinutes) {
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא");
        }



        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש לא מורה");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime start;
        start = LocalDateTime.parse(testStartTime, fmt);
//        try {
//            start = LocalDateTime.parse(testStartTime, fmt);   // המרה
//        } catch (DateTimeParseException e) {
//            return new BasicResponse(false, "פורמט תאריך לא חוקי — פורמט נדרש: yyyy-MM-dd HH:mm");
//        }

        LocalDateTime now = LocalDateTime.now();
        if (start.isBefore(now)) {
            return new BasicResponse(false,
                    "לא ניתן לקבוע מבחן לזמן שכבר עבר");
        }
        long minutesDiff  = Duration.between(now, start).toMinutes();

        if (minutesDiff < 120) {
            return new BasicResponse(false, "יש לקבוע את מועד המבחן לפחות שעתיים מראש");
        }
        TeacherEntity teacher = (TeacherEntity) user;

        List<StudentEntity> students = studentRepository.findStudentsByUserIds(usersIds);
        if (students.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו תלמידים עם ה-תעודות הזהות שנרשמו");
        }

        if (subject == null || subject.isEmpty()) {
            return new BasicResponse(false, "הנושא לא הוזן");
        }
        if (topic == null || topic.isEmpty()) {
            return new BasicResponse(false, "הנושא המשני לא הוזן");
        }
        if (difficulty == null || difficulty.isEmpty()) {
            return new BasicResponse(false, "דרגת הקושי לא הוזנה");
        }
        if (questionCount <= 0) {
            return new BasicResponse(false, "מספר השאלות חייב להיות גדול מאפס");
        }
        if (timeLimitMinutes <= 0) {
            return new BasicResponse(false, "זמן המבחן חייב להיות גדול מאפס");
        }


        TeacherTestEntity testEntity = new TeacherTestEntity();
        testEntity.setTeacher(teacher);
        testEntity.setStartTime(testStartTime);
        testEntity.setTimeLimitMinutes(timeLimitMinutes);
        testEntity.setSubject(subject);
        testEntity.setTopic(topic);
        testEntity.setDifficulty(difficulty);
        testEntity.setQuestionCount(questionCount);
        testEntity.setStudents(students);

        teacherTestRepository.save(testEntity);

        Random random = new Random();
        SubjectQuestionGenerator generatorFactory = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        List<TestQuestionEntity> questions = new ArrayList<>();
        List<String> subTopics = getSubTopics(topic);
        int[] difficultyLevels = getDifficultyLevels(difficulty);

        for (int i = 0; i < questionCount; i++) {
            int level = difficultyLevels[random.nextInt(difficultyLevels.length)];
            String subTopic = subTopics.get(random.nextInt(subTopics.size()));
            QuestionGenerator questionGenerator = generatorFactory.getQuestionGenerator(topic, subTopic);
            QuestionEntity questionEntity = questionGenerator.generateQuestion(level);

            TestQuestionEntity testQuestionEntity = questionEntityToTestQuestionMapper.apply(questionEntity);
            testQuestionEntity.setTest(testEntity);

            questionRepository.save(testQuestionEntity);
            questions.add(testQuestionEntity);
        }


        testEntity.setQuestions(questions);


        for (StudentEntity student : students) {
            student.getTeacherTests().add(testEntity);

           List<StudentTestStatusDTO> studentTestStatusDTOS=studentEntityToStudentTestStatusDTOMapper.apply(student);
            StudentTestStatusDTO studentTestStatusDTO=studentTestStatusDTOS.get(studentTestStatusDTOS.size() - 1);

            NotificationDTO<NewTestMessageDTO> dto =
                    new NotificationDTO<>(NotificationType.NEW_TEST, new NewTestMessageDTO(studentTestStatusDTO));
            notificationEventPublisher.publish(
                    List.of(student.getUserId()),
                    dto
            );
        }
        teacherTestRepository.save(testEntity);


        Map<TestEntity, List<TestQuestionEntity>> testMap = Map.of(testEntity, questions);
        TestDTO testDTO = testEntityToTestDTOMapper.apply(testMap);
        BasicResponse basicResponse = new BasicResponse(true, "המבחן נוצר בהצלחה ונישלח לתלמידים");
        basicResponse.setData(testDTO);
        return basicResponse;
    }


    public BasicResponse getTeacherSchedule(String teacherId) {


        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא");
        }

        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש לא מורה");
        }

        TeacherEntity teacher = (TeacherEntity) user;

        if(teacher.getLessons().isEmpty()){
            return new BasicResponse(false,"אין כרגע שיעורים");
        }

        Map<DayOfWeek, List<LessonDTO>> lessonsByDay = lessonsToScheduleMapper.apply(teacher.getLessons());


        BasicResponse response = new BasicResponse(true, null);
        response.setData(lessonsByDay);
        return response;
    }

    public BasicResponse getTeacherTeachingSubjects(String teacherId) {

        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null) {
            return new BasicResponse(false, "המורה לא נמצא");
        }

        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש לא מורה");
        }

        TeacherEntity teacher = (TeacherEntity) user;

        List<String> subjects=teacher.getTeachingSubjects();
        BasicResponse response = new BasicResponse(true, null);
        response.setData(subjects);
        return response;
    }



    public static List<String> getSubTopics(String topic) {
        switch (topic.toLowerCase()) {
            case "מספרים שלמים":
                return List.of(
                        "חיבור מספרים שלמים",
                        "חיסור מספרים שלמים",
                        "כפל מספרים שלמים",
                        "חילוק מספרים שלמים"
                );
            default:
                throw new IllegalArgumentException("נושא לא נתמך: ");
        }
    }

    private static int[] getDifficultyLevels(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "קל" -> new int[]{1, 2};
            case "בינוני" -> new int[]{3, 4};
            case "קשה" -> new int[]{4, 5};
            default -> throw new IllegalArgumentException("רמת קושי לא נתמכת: " + difficulty);
        };
    }

}
