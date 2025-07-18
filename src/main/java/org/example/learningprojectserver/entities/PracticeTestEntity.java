package org.example.learningprojectserver.entities;


import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;


}
