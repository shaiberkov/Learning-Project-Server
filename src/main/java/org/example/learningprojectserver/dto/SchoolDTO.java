package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {

    private String schoolName;
    private String schoolCode;
    private int teacherCount;
    private int studentCount;

}
