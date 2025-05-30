package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolGradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gradeName; // למשל: "א'", "ז'", "י"ב"

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
    private List<ClassRoomEntity> classes=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

}
