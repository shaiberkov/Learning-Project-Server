package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class ClassRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private SchoolGradeEntity grade;

    @ManyToMany(mappedBy = "teachingClassRooms")
    private List<TeacherEntity> teachers=new ArrayList<>();

    @OneToMany(mappedBy = "classRoom")
    private List<StudentEntity> students=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @OneToOne(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private ScheduleEntity schedule;
    public ClassRoomEntity() {

    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolGradeEntity getGrade() {
        return grade;
    }

    public void setGrade(SchoolGradeEntity grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }
}
