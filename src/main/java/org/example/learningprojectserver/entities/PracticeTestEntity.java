package org.example.learningprojectserver.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PRACTICE")
public class PracticeTestEntity extends TestEntity {


    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;



    public PracticeTestEntity() {
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }


}
