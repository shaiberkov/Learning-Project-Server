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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private SchoolGradeEntity grade;

    @ManyToMany(mappedBy = "teachingClassRooms")
    private List<TeacherEntity> teachers=new ArrayList<>();

    @OneToMany(mappedBy = "classRoom",fetch = FetchType.LAZY)
    private List<StudentEntity> students=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @OneToOne(mappedBy = "classRoom",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ScheduleEntity schedule;

}
