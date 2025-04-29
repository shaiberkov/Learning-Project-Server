package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class SchoolManagerEntity extends UserEntity {
    @OneToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;


public SchoolManagerEntity() {

}

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }


}
