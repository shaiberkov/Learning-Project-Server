package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
public class StudentEntity extends UserEntity {

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoomEntity classRoom;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolName;

    @ManyToMany(mappedBy = "students")  // מיפוי עם TeacherEntity
    private List<TeacherEntity> teachers=new ArrayList<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentProgressEntity studentProgressEntity;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentQuestionHistoryEntity studentQuestionHistoryEntity;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PracticeTestResultEntity> practiceTestsResult=new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TeacherTestResultEntity> teacherTestsResult=new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PracticeTestEntity> practiceTests=new ArrayList<>();

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private List<TeacherTestEntity> teacherTests=new ArrayList<>();


    public List<PracticeTestResultEntity> getPracticeTestsResult() {
        return practiceTestsResult;
    }

    public void setPracticeTestsResult(List<PracticeTestResultEntity> practiceTestsResult) {
        this.practiceTestsResult = practiceTestsResult;
    }

    public List<TeacherTestResultEntity> getTeacherTestsResult() {
        return teacherTestsResult;
    }

    public void setTeacherTestsResult(List<TeacherTestResultEntity> teacherTestsResult) {
        this.teacherTestsResult = teacherTestsResult;
    }

//    public StudentEntity() {}
//

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

    public StudentProgressEntity getStudentProgressEntity() {
        return studentProgressEntity;
    }

    public void setStudentProgressEntity(StudentProgressEntity studentProgressEntity) {
        this.studentProgressEntity = studentProgressEntity;
    }

    public StudentQuestionHistoryEntity getQuestionHistoryEntity() {
        return studentQuestionHistoryEntity;
    }

    public void setQuestionHistoryEntity(StudentQuestionHistoryEntity studentQuestionHistoryEntity) {
        this.studentQuestionHistoryEntity = studentQuestionHistoryEntity;
    }


    public StudentQuestionHistoryEntity getStudentQuestionHistoryEntity() {
        return studentQuestionHistoryEntity;
    }

    public void setStudentQuestionHistoryEntity(StudentQuestionHistoryEntity studentQuestionHistoryEntity) {
        this.studentQuestionHistoryEntity = studentQuestionHistoryEntity;
    }

    public List<PracticeTestEntity> getPracticeTests() {
        return practiceTests;
    }

    public void setPracticeTests(List<PracticeTestEntity> practiceTests) {
        this.practiceTests = practiceTests;
    }

    public List<TeacherTestEntity> getTeacherTests() {
        return teacherTests;
    }

    public void setTeacherTests(List<TeacherTestEntity> teacherTests) {
        this.teacherTests = teacherTests;
    }


}
