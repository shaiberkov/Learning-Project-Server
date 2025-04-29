package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

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
    private List<ClassRoomEntity> teachingClassRooms;


    @ManyToMany
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<StudentEntity> students;

    @ElementCollection
    @CollectionTable(name = "teacher_subject", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "subject_name")
    private List<String> teachingSubjects;




    @OneToMany(mappedBy = "teacher")
    private List<TestEntity> tests;

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

    public List<TestEntity> getTests() {
        return tests;
    }

    public void setTests(List<TestEntity> tests) {
        this.tests = tests;
    }

    public TeacherEntity() {}
}
