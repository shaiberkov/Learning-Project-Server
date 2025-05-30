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
public class
ClassRoomEntity {
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

}
