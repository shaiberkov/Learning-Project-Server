package org.example.learningprojectserver.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("PRACTICE_TEST_RESULT")
public class PracticeTestResultEntity extends TestResultEntity {

    @ManyToOne
    @JoinColumn(name = "student_id") // קשר Many-to-One (רבים ליחיד)
    private StudentEntity student;

    public PracticeTestResultEntity() {}

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
