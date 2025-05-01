package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.EntityMapper;
import org.example.learningprojectserver.mappers.UserMapperFactory;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchoolManagerService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolGradeRepository schoolGradeRepository;
    private final UserMapperFactory userMapperFactory;
    private final TeacherRepository teacherRepository;
    private final ClassRoomRepository classRoomRepository;

    @Autowired
    public SchoolManagerService(UserRepository userRepository, SchoolRepository schoolRepository, SchoolGradeRepository schoolGradeRepository, UserMapperFactory userMapperFactory, TeacherRepository teacherRepository, ClassRoomRepository classRoomRepository) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.schoolGradeRepository = schoolGradeRepository;
        this.userMapperFactory = userMapperFactory;
        this.teacherRepository = teacherRepository;
        this.classRoomRepository = classRoomRepository;
    }



    public BasicResponse assignUserAsSchoolTeacher(String userId, String schoolCode) {
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null) {
            return new BasicResponse(false, "יוזר לא נמצא במערכת");
        }

        if (user.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "המשתמש " + user.getUsername() + " משובץ כ־" + user.getRole());
        }

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר לא קיים במערכת");
        }

        EntityMapper<TeacherEntity> mapper = (EntityMapper<TeacherEntity>) userMapperFactory.getMapper(Role.TEACHER);
        TeacherEntity teacher = mapper.setSchool(school).map(user);

        school.getTeachers().add(teacher);

        userRepository.delete(user);
        userRepository.save(teacher);
        schoolRepository.save(school);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " שויך בהצלחה לבית הספר " + school.getSchoolName());
    }


    public BasicResponse removeTeacherFromSchool(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null) {
            return new BasicResponse(false, "המשתמש לא נמצא במערכת");
        }

        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש " + user.getUsername() + " אינו מורה");
        }

        TeacherEntity teacher = (TeacherEntity) user;
        SchoolEntity school = teacher.getTeachingSchool();

        if (school == null) {
            return new BasicResponse(false, "המורה " + teacher.getUsername() + " אינו משויך לאף בית ספר");
        }

        school.getTeachers().remove(teacher);
        teacher.setTeachingSchool(null);

        userRepository.save(teacher);
        schoolRepository.save(school);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " הוסר בהצלחה מהשיוך לבית הספר " + school.getSchoolName());
    }
    public BasicResponse addSchoolGrades(String schoolCode, List<String> gradeNames) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא קיים");
        }

        if (school.getSchoolGrades() == null) {
            school.setSchoolGrades(new ArrayList<>());
        }

        List<String> addedGrades = new ArrayList<>();
        for (String gradeName : gradeNames) {

            boolean alreadyExists = school.getSchoolGrades().stream()
                    .anyMatch(g -> g.getGradeName().equalsIgnoreCase(gradeName));

            if (!alreadyExists) {
                SchoolGradeEntity grade = new SchoolGradeEntity();
                grade.setGradeName(gradeName);
                grade.setSchool(school);

                school.getSchoolGrades().add(grade);
                addedGrades.add(gradeName);
            }
        }

        schoolRepository.save(school);

        if (addedGrades.isEmpty()) {
            return new BasicResponse(false, "לא נוספו שכבות (ייתכן שכבר קיימות)");
        }

        return new BasicResponse(true, "השכבות הבאות נוספו בהצלחה לבית הספר " + school.getSchoolName() + ": " + String.join(", ", addedGrades));
    }

    public BasicResponse assignTeacherToClass(String schoolCode, String teacherId, String className) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null || !(user instanceof TeacherEntity)) {
            return new BasicResponse(false, "המורה לא נמצא או שאינו מורה תקין");
        }
        TeacherEntity teacher = (TeacherEntity) user;

        if (teacher.getTeachingSchool() == null || !teacher.getTeachingSchool().getId().equals(school.getId())) {
            return new BasicResponse(false, "המורה אינו שייך לבית ספר זה");
        }

        ClassRoomEntity targetClass = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, className);
        if (targetClass == null) {
            return new BasicResponse(false, "כיתה בשם " + className + " לא נמצאה בבית ספר זה");
        }

        if (targetClass.getTeachers().contains(teacher)) {
            return new BasicResponse(false, "המורה " + teacher.getUsername() + " כבר משויך לכיתה " + targetClass.getName());
        }

        targetClass.getTeachers().add(teacher);
        teacher.getTeachingClassRooms().add(targetClass);

        classRoomRepository.save(targetClass);
        teacherRepository.save(teacher);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " שובץ בהצלחה לכיתה " + className);
    }


    public BasicResponse removeTeacherFromClass(String schoolCode, String teacherId, String className) {
        // שלב 1: מציאת בית הספר
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        // שלב 2: מציאת המורה
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null || !(user instanceof TeacherEntity)) {
            return new BasicResponse(false, "המורה לא נמצא או שאינו מורה תקין");
        }
        TeacherEntity teacher = (TeacherEntity) user;

        // בדיקה שהמורה שייך לבית הספר הזה
        if (teacher.getTeachingSchool() == null || !teacher.getTeachingSchool().getId().equals(school.getId())) {
            return new BasicResponse(false, "המורה אינו שייך לבית ספר זה");
        }

        // שלב 3: מציאת הכיתה
        ClassRoomEntity targetClass = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, className);
        if (targetClass == null) {
            return new BasicResponse(false, "כיתה בשם " + className + " לא נמצאה בבית ספר זה");
        }

        // בדיקה אם המורה לא משויך לכיתה
        if (!targetClass.getTeachers().contains(teacher)) {
            return new BasicResponse(false, "המורה " + teacher.getUsername() + " אינו משויך לכיתה " + targetClass.getName());
        }

        // שלב 4: הורדת המורה מהכיתה
        targetClass.getTeachers().remove(teacher);
        teacher.getTeachingClassRooms().remove(targetClass);

        // שלב 5: שמירה למסד הנתונים
        classRoomRepository.save(targetClass);
        teacherRepository.save(teacher);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " הוסר בהצלחה מכיתה " + className);
    }




    @Transactional
    public BasicResponse removeSchoolGrades(String schoolCode, List<String> gradeNames) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא קיים");
        }

        if (school.getSchoolGrades() == null || school.getSchoolGrades().isEmpty()) {
            return new BasicResponse(false, "אין שכבות להסיר");
        }

        List<String> removedGrades = new ArrayList<>();
        List<SchoolGradeEntity> gradesToRemove = new ArrayList<>();

        for (SchoolGradeEntity grade : school.getSchoolGrades()) {
            if (gradeNames.stream().anyMatch(name -> name.equalsIgnoreCase(grade.getGradeName()))) {
                gradesToRemove.add(grade);
                removedGrades.add(grade.getGradeName());
            }
        }
        schoolGradeRepository.deleteAll(gradesToRemove);
        school.getSchoolGrades().removeAll(gradesToRemove);

        schoolRepository.save(school);

        if (removedGrades.isEmpty()) {
            return new BasicResponse(false, "לא הוסרו שכבות (ייתכן שלא קיימות)");
        }

        return new BasicResponse(true, "השכבות הבאות הוסרו בהצלחה מבית הספר " + school.getSchoolName() + ": " + String.join(", ", removedGrades));
    }


    public BasicResponse addClassesToGrade(String schoolCode, String gradeName, int classesCount) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא קיים");
        }

        SchoolGradeEntity grade = school.getSchoolGrades().stream()
                .filter(g -> g.getGradeName().equals(gradeName))
                .findFirst()
                .orElse(null);

        if (grade == null) {
            return new BasicResponse(false, "השכבה " + gradeName + " לא קיימת בבית הספר");
        }

        if (grade.getClasses() == null) {
            grade.setClasses(new ArrayList<>());
        }

        Set<String> existingClassNames = grade.getClasses().stream()
                .map(ClassRoomEntity::getName)
                .collect(Collectors.toSet());

        List<String> addedClassNames = new ArrayList<>();

        for (int i = 0; i < classesCount; i++) {
            String className = gradeName + (i + 1);
            if (!existingClassNames.contains(className)) {
                ClassRoomEntity classRoom = new ClassRoomEntity();
                classRoom.setName(className);
                classRoom.setGrade(grade);
                classRoom.setSchool(school);
                grade.getClasses().add(classRoom);
                addedClassNames.add(className);
            }
        }

        schoolRepository.save(school);

        if (addedClassNames.isEmpty()) {
            return new BasicResponse(false, "לא נוספו כיתות — הכיתות קיימות כבר קיימים בשכבה");
        }

        return new BasicResponse(true, "הכיתות הבאות נוספו לשכבה " + gradeName + ": " + String.join(", ", addedClassNames));
    }


    public BasicResponse addAdditionalClassToGrade(String schoolCode, String gradeName, String className) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא קיים");
        }

        SchoolGradeEntity grade = school.getSchoolGrades().stream()
                .filter(g -> g.getGradeName().equalsIgnoreCase(gradeName))
                .findFirst()
                .orElse(null);

        if (grade == null) {
            return new BasicResponse(false, "השכבה " + gradeName + " לא קיימת בבית הספר");
        }

        if (grade.getClasses() == null) {
            grade.setClasses(new ArrayList<>());
        }

        boolean classExists = grade.getClasses().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(className));

        if (classExists) {
            return new BasicResponse(false, "כיתה בשם " + className + " כבר קיימת בשכבה " + gradeName);
        }

        ClassRoomEntity newClass = new ClassRoomEntity();
        newClass.setName(className);
        newClass.setGrade(grade);
        newClass.setSchool(school);

        grade.getClasses().add(newClass);
        schoolRepository.save(school);

        return new BasicResponse(true, "כיתה " + className + " נוספה בהצלחה לשכבה " + gradeName);
    }


}
