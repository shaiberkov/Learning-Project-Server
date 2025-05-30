package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SchoolManagerEntity extends UserEntity {
    @OneToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;


}
