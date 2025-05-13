package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
import org.example.learningprojectserver.dto.StudentDTO;
import org.example.learningprojectserver.dto.TeacherDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.Mapper;
import org.example.learningprojectserver.mappers.StudentEntityToStudentDTOMapper;
import org.example.learningprojectserver.mappers.TeacherEntityToTeacherDTOMapper;
import org.example.learningprojectserver.mappers.UserMapperFactory;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final StudentRepository studentRepository;
    private final TeacherEntityToTeacherDTOMapper teacherEntityToTeacherDTOMapper;
    private final StudentEntityToStudentDTOMapper studentEntityToStudentDTOMapper;
    private final SchoolManagerRepository schoolManagerRepository;

    @Autowired
    public SchoolManagerService(UserRepository userRepository, SchoolRepository schoolRepository, SchoolGradeRepository schoolGradeRepository, UserMapperFactory userMapperFactory, TeacherRepository teacherRepository, ClassRoomRepository classRoomRepository, StudentRepository studentRepository, TeacherEntityToTeacherDTOMapper teacherEntityToTeacherDTOMapper, StudentEntityToStudentDTOMapper studentEntityToStudentDTOMapper, SchoolManagerRepository schoolManagerRepository) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.schoolGradeRepository = schoolGradeRepository;
        this.userMapperFactory = userMapperFactory;
        this.teacherRepository = teacherRepository;
        this.classRoomRepository = classRoomRepository;
        this.studentRepository = studentRepository;
        this.teacherEntityToTeacherDTOMapper = teacherEntityToTeacherDTOMapper;
        this.studentEntityToStudentDTOMapper = studentEntityToStudentDTOMapper;
        this.schoolManagerRepository = schoolManagerRepository;
    }

public BasicResponse getSchoolCode(String userId){

   String schoolCode= schoolManagerRepository.findSchoolCodeByUserId(userId);
return new BasicResponse(true, schoolCode);
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

        Mapper<UserEntity, TeacherEntity> mapper = userMapperFactory.getMapper(Role.TEACHER);
        TeacherEntity teacher = mapper.apply(user);
        teacher.setTeachingSchool(school);

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

        // ניתוק המורה מהבית ספר
        school.getTeachers().remove(teacher);
        teacher.setTeachingSchool(null);
        schoolRepository.save(school);

        // המרה לסטודנט
        Mapper<UserEntity, StudentEntity> mapper = userMapperFactory.getMapper(Role.STUDENT);
        StudentEntity student = mapper.apply(user);

        // הסרה של ה-Teacher ושמירה של ה-Student
        userRepository.delete(teacher);
        userRepository.save(student);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " הוסר בהצלחה" + school.getSchoolName());
    }


//    public BasicResponse removeTeacherFromSchool(String userId) {
//        UserEntity user = userRepository.findUserByUserId(userId);
//
//        if (user == null) {
//            return new BasicResponse(false, "המשתמש לא נמצא במערכת");
//        }
//
//        if (user.getRole() != Role.TEACHER) {
//            return new BasicResponse(false, "המשתמש " + user.getUsername() + " אינו מורה");
//        }
////Todo מורה שהסירו אותו תהפוך אותו בחזרה לסטודנט
////        Mapper<UserEntity, StudentEntity> mapper = userMapperFactory.getMapper(Role.STUDENT);
//
//        TeacherEntity teacher = (TeacherEntity) user;
//        SchoolEntity school = teacher.getTeachingSchool();
//
//        if (school == null) {
//            return new BasicResponse(false, "המורה " + teacher.getUsername() + " אינו משויך לאף בית ספר");
//        }
//
//
//        school.getTeachers().remove(teacher);
//        teacher.setTeachingSchool(null);
//
//        userRepository.save(teacher);
//        schoolRepository.save(school);
//
//        return new BasicResponse(true, "המורה " + teacher.getUsername() + " הוסר בהצלחה מהשיוך לבית הספר " + school.getSchoolName());
//    }
    public BasicResponse addSchoolGrades(String schoolCode, List<String> gradeNames) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא קיים");
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

//    public BasicResponse assignTeacherToClass(String schoolCode, String teacherId, String className) {
//        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
//        if (school == null) {
//            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
//        }
//
//        UserEntity user = userRepository.findUserByUserId(teacherId);
//        if (user == null ) {
//            return new BasicResponse(false, "המורה לא נמצא או שאינו מורה תקין");
//        }
//        TeacherEntity teacher = (TeacherEntity) user;
//
//        if (teacher.getTeachingSchool() == null || !teacher.getTeachingSchool().getId().equals(school.getId())) {
//            return new BasicResponse(false, "המורה אינו שייך לבית ספר זה");
//        }
//
//        ClassRoomEntity targetClass = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, className);
//        if (targetClass == null) {
//            return new BasicResponse(false, "כיתה בשם " + className + " לא נמצאה בבית ספר זה");
//        }
//
//        if (targetClass.getTeachers().contains(teacher)) {
//            return new BasicResponse(false, "המורה " + teacher.getUsername() + " כבר משויך לכיתה " + targetClass.getName());
//        }
//
//        targetClass.getTeachers().add(teacher);
//        teacher.getTeachingClassRooms().add(targetClass);
//
//        classRoomRepository.save(targetClass);
//
//        return new BasicResponse(true, "המורה " + teacher.getUsername() + " שובץ בהצלחה לכיתה " + className);
//    }
//



    public BasicResponse assignTeacherToClasses(String schoolCode, String teacherId, List<String> classNames) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null||user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המורה לא נמצא או שאינו מורה תקין");
        }

        TeacherEntity teacher = (TeacherEntity) user;

        if (teacher.getTeachingSchool() == null || !teacher.getTeachingSchool().getId().equals(school.getId())) {
            return new BasicResponse(false, "המורה אינו שייך לבית ספר זה");
        }

        List<ClassRoomEntity> targetClasses = classRoomRepository.findBySchoolCodeAndClassNames(schoolCode, classNames);
        if (targetClasses == null || targetClasses.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו כיתות תואמות בבית הספר המבוקש");
        }

        List<String> newlyAssigned = new ArrayList<>();
        List<String> alreadyAssigned = new ArrayList<>();

        for (ClassRoomEntity classRoom : targetClasses) {
            if (classRoom.getTeachers().contains(teacher)) {
                alreadyAssigned.add(classRoom.getName());
            } else {
                classRoom.getTeachers().add(teacher);
                teacher.getTeachingClassRooms().add(classRoom);

                for (StudentEntity student : classRoom.getStudents()) {
                    if (!teacher.getStudents().contains(student)) {
                        teacher.getStudents().add(student);
                    }
                    if (!student.getTeachers().contains(teacher)) {
                        student.getTeachers().add(teacher);
                    }
                }

                newlyAssigned.add(classRoom.getName());
            }
        }

        if (!newlyAssigned.isEmpty()) {
            teacherRepository.save(teacher);
        }

        String message = "המורה " + teacher.getUsername();

        if (!newlyAssigned.isEmpty()) {
            message += " שובץ בהצלחה לכיתות: " + String.join(", ", newlyAssigned);
        }

        if (!alreadyAssigned.isEmpty()) {
            message += ". לא שובץ לכיתות: " + String.join(", ", alreadyAssigned) + " כיוון שכבר היה משויך אליהן";
        }

        return new BasicResponse(true, message);
    }
    //TODO ליראות שעובד והכל נישמר נכון
    public BasicResponse removeTeacherFromClass(String schoolCode, String teacherId, String className) {
        // שלב 1: מציאת בית הספר
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        // שלב 2: מציאת המורה
        UserEntity user = userRepository.findUserByUserId(teacherId);
        if (user == null ) {
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

    public BasicResponse assignStudentToClass(String schoolCode, String studentId, String className) {

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        UserEntity user = userRepository.findUserByUserId(studentId);

        if (user == null || user.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "המשתמש לא נמצא או שאינו תלמיד");
        }
        StudentEntity student = (StudentEntity) user;

        if (student.getClassRoom() != null && student.getClassRoom().getName().equals(className)) {
            return new BasicResponse(false, "הסטודנט כבר משויך לכיתה זו");
        }

        ClassRoomEntity targetClass = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, className);

        if (targetClass == null) {
            return new BasicResponse(false, "כיתה בשם " + className + " לא נמצאה");
        }

        for (TeacherEntity teacher : targetClass.getTeachers()) {
            if (!teacher.getStudents().contains(student)) {
                teacher.getStudents().add(student);
            }

            if (!student.getTeachers().contains(teacher)) {
                student.getTeachers().add(teacher);
            }
        }
        targetClass.getStudents().add(student);
        student.setClassRoom(targetClass);
        student.setSchoolName(school);
        studentRepository.save(student);

        return new BasicResponse(true, "התלמיד " + student.getUsername() + " שויך בהצלחה לכיתה " + className);
    }


    public BasicResponse changeStudentClass(String schoolCode, String studentId, String newGradeName, String newClassName) {

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
        }

        UserEntity user = userRepository.findUserByUserId(studentId);
        if (user == null || user.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "המשתמש לא נמצא או שאינו תלמיד");
        }
        StudentEntity student = (StudentEntity) user;

        SchoolGradeEntity newGrade = school.getSchoolGrades().stream()
                .filter(g -> g.getGradeName().equalsIgnoreCase(newGradeName))
                .findFirst()
                .orElse(null);
        if (newGrade == null) {
            return new BasicResponse(false, "השכבה " + newGradeName + " לא קיימת בבית הספר");
        }

        ClassRoomEntity newClass = newGrade.getClasses().stream()
                .filter(c -> c.getName().equalsIgnoreCase(newClassName))
                .findFirst()
                .orElse(null);
        if (newClass == null) {
            return new BasicResponse(false, "כיתה בשם " + newClassName + " לא נמצאה בשכבה " + newGradeName);
        }

        ClassRoomEntity currentClass = student.getClassRoom();
        if (currentClass != null) {
            currentClass.getStudents().remove(student);
        }

        student.setClassRoom(newClass);
        newClass.getStudents().add(student);

        studentRepository.save(student);

        return new BasicResponse(true, "התלמיד " + student.getUsername() + " עבר בהצלחה לכיתה " + newClassName + " בשכבה " + newGradeName);
    }



    public BasicResponse getAllTeachers(String schoolCode) {

    SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
    if (school == null) {
        return new BasicResponse(false,"בית ספר זה לא נימצא במערכת");
    }

    List<TeacherEntity> teachers = school.getTeachers();
    if (teachers == null|| teachers.isEmpty()) {
        return new BasicResponse(false,"לא קיימים מורים בבית ספר זה");
    }

    List<TeacherDTO> teacherDTOs = teachers.stream()
            .map(teacherEntityToTeacherDTOMapper).toList();

    BasicResponse basicResponse=new BasicResponse(true,null);
    basicResponse.setData(teacherDTOs);
    return basicResponse;
    }

    public BasicResponse getAllStudents(String schoolCode) {

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false,"בית ספר זה לא נימצא במערכת");
        }
        List<StudentEntity> students = school.getStudents();
        if (students == null|| students.isEmpty()) {
            return new BasicResponse(false,"לא קיימים תלמידים בבית ספר זה");
        }
        List<StudentDTO> studentDTOs = students.stream()
                .map(studentEntityToStudentDTOMapper).toList();

        BasicResponse basicResponse=new BasicResponse(true,null);
        basicResponse.setData(studentDTOs);
        return basicResponse;
    }


}
