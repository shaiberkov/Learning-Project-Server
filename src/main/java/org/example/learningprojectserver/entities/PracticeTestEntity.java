package org.example.learningprojectserver.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PRACTICE")
@Getter
@Setter
@NoArgsConstructor
public class PracticeTestEntity extends TestEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;


}
