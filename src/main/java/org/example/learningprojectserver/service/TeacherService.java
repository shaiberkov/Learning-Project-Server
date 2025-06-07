package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ClassRoomRepository classRoomRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final TeacherTestRepository teacherTestRepository;
    private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
    private final TeacherEntityToTeacherDTOMapper teacherEntityToTeacherDTOMapper;
    private final SchoolRepository schoolRepository;
    private final LessonsToScheduleMapper lessonsToScheduleMapper;
    private final StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper;
    private final NotificationEventPublisher notificationEventPublisher;
    private final TestService testService;
    private final CacheManager cacheManager;
    private final TeacherRepository teacherRepository;



public BasicResponse getTeacherDTO(String teacherId,String schoolCode) {

   TeacherDTO teacherDTO= teacherRepository.findTeacherDTOByUserIdAndSchoolCode(teacherId,schoolCode);
   if(teacherDTO != null) {
       BasicResponse basicResponse = new BasicResponse(true, null);
       basicResponse.setData(teacherDTO);
       return basicResponse;
   }
    BasicResponse basicResponse = new BasicResponse(true, "לא קיים מורה כזה בבית ספר");

    return basicResponse;
}



    public BasicResponse addTeachingSubjectToTeacher(String teacherId, List<String> subjects) {

        TeacherEntity teacher = teacherRepository.findByUserIdWithSubjects(teacherId);
        if (teacher == null) {
            return new BasicResponse(false, "המורה לא נמצא במערכת");
        }

        List<String> subjectsOfTeacher=teacher.getTeachingSubjects();
        if (!Collections.disjoint(subjectsOfTeacher, subjects)) {
            return new BasicResponse(false,"לא ניתן להוסיף את המקצועות משום שחלק מהם כבר נמצאים ברשימה של המורה: " + String.join(", ", subjectsOfTeacher));
        }
        teacher.getTeachingSubjects().addAll(subjects);
        userRepository.save(teacher);

        return new BasicResponse(true, "המקצועות נוספו בהצלחה");
    }

    public BasicResponse removeTeachingSubjectFromTeacher(String teacherId,String subjectToRemove) {

        TeacherEntity teacher = teacherRepository.findByUserIdWithSubjects(teacherId);
        if (teacher == null) {
            return new BasicResponse(false, "המורה לא נמצא במערכת");
        }
        if (!teacher.getTeachingSubjects().contains(subjectToRemove)) {
            return new BasicResponse(false, "המורה אינו מלמד מקצוע זה ");
        }

        teacher.getTeachingSubjects().remove(subjectToRemove);
        userRepository.save(teacher);

        return new BasicResponse(true, "המקצוע הוסר בהצלחה מהמורה.");
    }



    @CacheEvict(value = "teacherSchedule", key = "#teacherId")
    @Transactional
    public BasicResponse addLessonToTeacher(LessonDTO dto, String teacherId) {
        TeacherEntity teacher=teacherRepository.findByUserId(teacherId);
        if (teacher == null) {
            return new BasicResponse(false, "מורה לא נמצא");
        }

        ClassRoomEntity classRoom = classRoomRepository.findClassRoomByName(dto.getClassRoomName());
        if (classRoom == null) {
            return new BasicResponse(false, "כיתה לא נמצאה");
        }

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
        for (StudentEntity pupil : classRoom.getStudents()) {
            cacheManager.getCache("studentSchedule")
                    .evict(pupil.getUserId());
        }
        cacheManager.getCache("classRoomSchedule")
                .evict(classRoom.getSchool().getSchoolCode() + "_" + classRoom.getName());


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





    @CacheEvict(
            value = "upcomingEvents",
            key = "'TEACHER_' + #teacherId"
    )
    @Transactional
    public BasicResponse generateTestForStudents(List<String> usersIds, String teacherId, String testStartTime,
                                                 String subject, String topic, String difficulty, int questionCount, int timeLimitMinutes) {




        TeacherEntity teacher=teacherRepository.findByUserId(teacherId);
        if (teacher == null) {
            return new BasicResponse(false, "המורה לא נמצא");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime start;
        start = LocalDateTime.parse(testStartTime, fmt);


        LocalDateTime now = LocalDateTime.now();
        if (start.isBefore(now)) {
            return new BasicResponse(false,
                    "לא ניתן לקבוע מבחן לזמן שכבר עבר");
        }
        long minutesDiff  = Duration.between(now, start).toMinutes();

        if (minutesDiff < 120) {
            return new BasicResponse(false, "יש לקבוע את מועד המבחן לפחות שעתיים מראש");
        }

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

        List<TestQuestionEntity> questions=testService.generateTestQuestions(subject,topic,questionCount,difficulty,testEntity);

        testEntity.setQuestions(questions);

        notifyStudentsAboutNewTest(students,testEntity);
        teacherTestRepository.save(testEntity);
        Cache upcomingEventsCache = cacheManager.getCache("upcomingEvents");
        Cache studentTestsCache = cacheManager.getCache("studentTestsStatus");
            for (String id : usersIds) {
                studentTestsCache.evict(id);
                upcomingEventsCache.evict("STUDENT_" + id);
        }

        Map<TestEntity, List<TestQuestionEntity>> testMap = Map.of(testEntity, questions);
        TestDTO testDTO = testEntityToTestDTOMapper.apply(testMap);
        BasicResponse basicResponse = new BasicResponse(true, "המבחן נוצר בהצלחה ונישלח לתלמידים");
        basicResponse.setData(testDTO);
        return basicResponse;
    }


    private void notifyStudentsAboutNewTest(List<StudentEntity> students, TeacherTestEntity test) {
        for (StudentEntity student : students) {
            student.getTeacherTests().add(test);

            List<StudentTestStatusDTO> studentTestStatusDTOS = studentRepository.findAllTestsForStudent(student.getUserId());

            StudentTestStatusDTO studentTestStatusDTO = studentTestStatusDTOS.get(studentTestStatusDTOS.size() - 1);

            NotificationDTO<NewTestMessageDTO> dto =
                    new NotificationDTO<>(NotificationType.NEW_TEST, new NewTestMessageDTO(studentTestStatusDTO));

            notificationEventPublisher.publish(
                    List.of(student.getUserId()),
                    dto
            );
        }
    }
    @Cacheable(value = "teacherSchedule", key = "#teacherId")
    public BasicResponse getTeacherSchedule(String teacherId) {

        List<LessonDTO> lessons = lessonRepository.findLessonsByTeacherId(teacherId);

        Map<DayOfWeek, List<LessonDTO>> lessonsByDay = lessons.stream()
                .collect(Collectors.groupingBy(
                        LessonDTO::getDayOfWeek,
                        () -> new TreeMap<>(Comparator.comparingInt(day -> day == DayOfWeek.SUNDAY ? 0 : day.getValue())),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(LessonDTO::getStartTime))
                                        .collect(Collectors.toList())
                        )
                ));


        BasicResponse response = new BasicResponse(true, null);
        response.setData(lessonsByDay);
        return response;
    }


    public BasicResponse getTeacherTeachingSubjects(String teacherId) {

        TeacherEntity teacher = teacherRepository.findByUserIdWithSubjects(teacherId);
        if (teacher == null) {
            return new BasicResponse(false, "המורה לא נמצא");
        }

        List<String> subjects=teacher.getTeachingSubjects();
        BasicResponse response = new BasicResponse(true, null);
        response.setData(subjects);
        return response;
    }

}
