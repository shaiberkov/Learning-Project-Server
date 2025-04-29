package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class StudentEntity extends UserEntity {

//    private Long id;
//
//    private ClassRoomEntity classRoom;
//
//    private String classGrade;
//
//    private SchoolManagerEntity schoolManager;
//
//    private SchoolEntity schoolName;
//
//    private List<TeacherEntity> teachers;

    @ManyToOne
    @JoinColumn(name = "class_room_id") // עמודה שתצביע על הכיתה
    private ClassRoomEntity classRoom;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolName;

    @ManyToMany(mappedBy = "students")  // מיפוי עם TeacherEntity
    private List<TeacherEntity> teachers;



    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private UserProgressEntity studentProgressEntity;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private QuestionHistoryEntity questionHistoryEntity;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<TestResultEntity> studentTestsResult;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<TestEntity> studentTests;






    public StudentEntity() {}


    public ClassRoomEntity getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoomEntity classRoom) {
        this.classRoom = classRoom;
    }

    public SchoolEntity getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(SchoolEntity schoolName) {
        this.schoolName = schoolName;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public UserProgressEntity getStudentProgressEntity() {
        return studentProgressEntity;
    }

    public void setStudentProgressEntity(UserProgressEntity studentProgressEntity) {
        this.studentProgressEntity = studentProgressEntity;
    }

    public QuestionHistoryEntity getQuestionHistoryEntity() {
        return questionHistoryEntity;
    }

    public void setQuestionHistoryEntity(QuestionHistoryEntity questionHistoryEntity) {
        this.questionHistoryEntity = questionHistoryEntity;
    }

    public List<TestResultEntity> getStudentTestsResult() {
        return studentTestsResult;
    }

    public void setStudentTestsResult(List<TestResultEntity> studentTestsResult) {
        this.studentTestsResult = studentTestsResult;
    }

    public List<TestEntity> getStudentTests() {
        return studentTests;
    }

    public void setStudentTests(List<TestEntity> studentTests) {
        this.studentTests = studentTests;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "classRoom=" + classRoom +
                ", schoolName=" + schoolName +
                ", teachers=" + teachers +
                ", studentProgressEntity=" + studentProgressEntity +
                ", questionHistoryEntity=" + questionHistoryEntity +
                ", studentTestsResult=" + studentTestsResult +
                ", studentTests=" + studentTests +
                '}';
    }
}
