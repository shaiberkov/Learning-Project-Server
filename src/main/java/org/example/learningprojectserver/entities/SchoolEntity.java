package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "school_entity", indexes = {
        @Index(name = "idx_school_code", columnList = "schoolCode")
})
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String schoolCode;

    @OneToMany(mappedBy = "teachingSchool",fetch = FetchType.EAGER)
    private List<TeacherEntity> teachers=new ArrayList<>();

    @OneToMany(mappedBy = "schoolName",fetch = FetchType.EAGER)
    private List<StudentEntity> students=new ArrayList<>();

    @OneToMany(mappedBy = "school",fetch = FetchType.EAGER)
    private List<ClassRoomEntity> classRooms=new ArrayList<>();

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SchoolGradeEntity> schoolGrades=new ArrayList<>();


    @OneToOne(mappedBy = "school")
    private SchoolManagerEntity schoolManager;


    public SchoolEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SchoolGradeEntity> getSchoolGrades() {
        return schoolGrades;
    }

    public void setSchoolGrades(List<SchoolGradeEntity> schoolGrades) {
        this.schoolGrades = schoolGrades;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<ClassRoomEntity> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoomEntity> classRooms) {
        this.classRooms = classRooms;
    }

    public SchoolManagerEntity getSchoolManager() {
        return schoolManager;
    }

    public void setSchoolManager(SchoolManagerEntity schoolManager) {
        this.schoolManager = schoolManager;
    }
}

