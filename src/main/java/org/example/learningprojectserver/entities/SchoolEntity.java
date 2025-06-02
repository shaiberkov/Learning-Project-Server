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
@Table(name = "school_entity", indexes = {
        @Index(name = "idx_school_code", columnList = "schoolCode")
})
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String schoolCode;

    @OneToMany(mappedBy = "teachingSchool",fetch = FetchType.LAZY)
    private List<TeacherEntity> teachers=new ArrayList<>();

    @OneToMany(mappedBy = "schoolName",fetch = FetchType.LAZY)
    private List<StudentEntity> students=new ArrayList<>();

    @OneToMany(mappedBy = "school",fetch = FetchType.LAZY)
    private List<ClassRoomEntity> classRooms=new ArrayList<>();

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SchoolGradeEntity> schoolGrades=new ArrayList<>();


    @OneToOne(mappedBy = "school",fetch = FetchType.LAZY)
    private SchoolManagerEntity schoolManager;

}

