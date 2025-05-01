package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class SchoolGradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gradeName; // למשל: "א'", "ז'", "י"ב"

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
    private List<ClassRoomEntity> classes;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    public SchoolGradeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<ClassRoomEntity> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassRoomEntity> classes) {
        this.classes = classes;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }
}
