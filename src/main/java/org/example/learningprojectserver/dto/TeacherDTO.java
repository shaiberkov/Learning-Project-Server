package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private String teacherName;
    private String email;
    private String phone;
    private List<String> subjects;
    private List<String> classes;
    private List<String> grades;

}
