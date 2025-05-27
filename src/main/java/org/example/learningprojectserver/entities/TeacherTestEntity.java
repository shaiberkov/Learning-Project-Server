package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
public class TeacherTestEntity extends TestEntity {



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_test_student",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<StudentEntity> students = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    public TeacherTestEntity() {}




    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }
}
