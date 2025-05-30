package org.example.learningprojectserver.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PRACTICE_TEST_RESULT")
@Getter
@Setter
@NoArgsConstructor
public class PracticeTestResultEntity extends TestResultEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

}
