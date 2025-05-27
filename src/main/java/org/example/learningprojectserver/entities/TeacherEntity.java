package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TeacherEntity extends UserEntity {
    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity teachingSchool;

    @ManyToMany
    @JoinTable(
            name = "teacher_classroom",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private List<ClassRoomEntity> teachingClassRooms = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<StudentEntity> students = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_subject", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "subject_name")
    private List<String> teachingSubjects=new ArrayList<>();


    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private List<TeacherTestEntity> tests = new ArrayList<>();

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private List<LessonEntity> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<TeacherTestResultEntity> testResults=new ArrayList<>();

    public List<TeacherTestResultEntity> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TeacherTestResultEntity> testResults) {
        this.testResults = testResults;
    }

    public SchoolEntity getTeachingSchool() {
        return teachingSchool;
    }

    public void setTeachingSchool(SchoolEntity teachingSchool) {
        this.teachingSchool = teachingSchool;
    }

    public List<ClassRoomEntity> getTeachingClassRooms() {
        return teachingClassRooms;
    }

    public void setTeachingClassRooms(List<ClassRoomEntity> teachingClassRooms) {
        this.teachingClassRooms = teachingClassRooms;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<String> getTeachingSubjects() {
        return teachingSubjects;
    }

    public void setTeachingSubjects(List<String> teachingSubjects) {
        this.teachingSubjects = teachingSubjects;
    }

    public List<TeacherTestEntity> getTests() {
        return tests;
    }

    public void setTests(List<TeacherTestEntity> tests) {
        this.tests = tests;
    }

    public List<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonEntity> lessons) {
        this.lessons = lessons;
    }

    public TeacherEntity() {
    }

}
