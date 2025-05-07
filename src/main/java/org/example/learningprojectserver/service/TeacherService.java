package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.*;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGeneratorFactory;
import org.example.learningprojectserver.service.QuestionGenerator.SubjectQuestionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    public TeacherService(LessonRepository lessonRepository, UserRepository userRepository, ClassRoomRepository classRoomRepository, ScheduleRepository scheduleRepository, StudentRepository studentRepository, TeacherTestRepository teacherTestRepository, QuestionRepository questionRepository, QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper, LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper, QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper, TestEntityToTestDTOMapper testEntityToTestDTOMapper) {
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
    }

    public BasicResponse getLessonsForTeacher(String teacherId) {

        UserEntity teacher = userRepository.findUserByUserId(teacherId);
        if (teacher == null) {

            return new BasicResponse(false, "מורה לא נימצא");
        }

        List<LessonEntity> lessonEntities = lessonRepository.findLessonsByTeacherId(teacherId);

        if (lessonEntities.isEmpty()) {
            return new BasicResponse(false, "אין שיעורים למורה זה");
        }


        List<LessonDTO> lessonDTOs = lessonEntities.stream()
                .map(lessonEntityToLessonDTOMapper).toList();

        BasicResponse basicResponse = new BasicResponse(true, null);
        basicResponse.setData(lessonDTOs);
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

//TODO לסיים לעבור על זה ליראות שהכל עובד
    @Transactional
    public BasicResponse addLessonToTeacher(LessonDTO dto, String teacherId) {
        UserEntity user = userRepository.findUserByUserId(teacherId);

        if (user == null) {
            return new BasicResponse(false, "מורה לא נמצא");
        }
//לתקן כי לפי קוד ולפי של בית ספר ושל שם כיתה
        ClassRoomEntity classRoom = classRoomRepository.findClassRoomByName(dto.getClassRoomName());
        if (classRoom == null) {
            return new BasicResponse(false, "כיתה לא נמצאה");
        }

        TeacherEntity teacher = (TeacherEntity) user;

        if (!teacher.getTeachingSubjects().contains(dto.getSubject())) {
            return new BasicResponse(false, "המורה אינו מוסמך ללמד את המקצוע '" + dto.getSubject() + "'");
        }
        // ניתוח נתונים מתוך DTO
        DayOfWeek dayOfWeek = dto.getDayOfWeek();
        LocalTime startTime = LocalTime.parse(dto.getStartTime());
        LocalTime endTime = LocalTime.parse(dto.getEndTime());

        if (DayOfWeek.SATURDAY==dayOfWeek) {
            return new BasicResponse(false,"לא ניתן לשבץ לשיעור ביום שבת");
        }


        ScheduleEntity schedule = classRoom.getSchedule();
        if (schedule == null) {
            schedule = new ScheduleEntity();
            schedule.setClassRoom(classRoom);
            schedule = scheduleRepository.save(schedule);
            classRoom.setSchedule(schedule);
            classRoomRepository.save(classRoom);
        }


        for (LessonEntity existingLesson : teacher.getLessons()) {
            if (existingLesson.getDayOfWeek() == dayOfWeek) {
                boolean overlaps = !(endTime.isBefore(existingLesson.getStartTime()) || startTime.isAfter(existingLesson.getEndTime()));
                if (overlaps) {
                    return new BasicResponse(false, "קיים כבר שיעור אחר למורה בזמן זה");
                }
            }
        }

        // בדיקה אם קיים שיעור חופף אצל הכיתה
        for (LessonEntity existingLesson : schedule.getLessons()) {
            if (existingLesson.getDayOfWeek() == dayOfWeek) {
                boolean overlaps = !(endTime.isBefore(existingLesson.getStartTime()) || startTime.isAfter(existingLesson.getEndTime()));
                if (overlaps) {
                    return new BasicResponse(false, "קיים כבר שיעור אחר בכיתה בזמן זה");
                }
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






    //@PostConstruct()
    //public void init() {
//        addTeachingSubjectToTeacher("310594015",List.of("מתמטיקה","עיברית"));
//        LessonDTO dto = new LessonDTO("מתמללללטיקה", DayOfWeek.THURSDAY.toString(), "10:00", "11:00");
//        dto.setClassRoomName("ג'1");
//        System.out.println(addLessonToTeacher(dto, "310594015"));

    //}



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
        TeacherEntity teacher = (TeacherEntity) user;

        List<StudentEntity> students = studentRepository.findStudentsByUserIds(usersIds);
        if (students.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו תלמידים עם ה-IDים שסופקו");
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

        // קביעת התלמידים במבחן
        testEntity.setStudents(students);

        // שמירה של המבחן במאגר
        teacherTestRepository.save(testEntity);

        // יצירת שאלות למבחן
        Random random = new Random();
        SubjectQuestionGenerator generatorFactory = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        List<TestQuestionEntity> questions = new ArrayList<>();
        List<String> subTopics = getSubTopics(topic);
        int[] difficultyLevels = getDifficultyLevels(difficulty);

        // יצירת שאלות עבור המבחן
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
        teacherTestRepository.save(testEntity);



        Map<TestEntity, List<TestQuestionEntity>> testMap = Map.of(testEntity, questions);
        TestDTO testDTO = testEntityToTestDTOMapper.apply(testMap);
        BasicResponse basicResponse = new BasicResponse(true, null);
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

        Map<DayOfWeek, List<LessonDTO>> lessonsByDay = teacher.getLessons().stream()
                .map(lessonEntityToLessonDTOMapper)
                .collect(Collectors.groupingBy(
                        LessonDTO::getDayOfWeek,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                (List<LessonDTO> list) -> list.stream()
                                        .sorted(Comparator.comparing(LessonDTO::getStartTime))
                                        .collect(Collectors.toList())
                        )
                ));


        BasicResponse response = new BasicResponse(true, null);
        response.setData(lessonsByDay);
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
