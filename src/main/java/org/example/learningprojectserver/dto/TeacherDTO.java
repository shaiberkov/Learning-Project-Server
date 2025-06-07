//package org.example.learningprojectserver.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class TeacherDTO {
//
//    private String teacherName;
//    private String email;
//    private String phone;
//    private List<String> subjects;
//    private List<String> classes;
//    private List<String> grades;
//
//}


package org.example.learningprojectserver.dto;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@NoArgsConstructor

public class TeacherDTO {
    private String teacherName;
    private String email;
    private String phone;
    private List<String> subjects;
    private List<String> classes;
    private List<String> grades;

    public TeacherDTO(String teacherName,
                      String email,
                      String phone,
                      String subjectsConcat,
                      String classesConcat,
                      String gradesConcat) {
        this.teacherName = teacherName;
        this.email = email;
        this.phone = phone;

        if (subjectsConcat == null || subjectsConcat.isBlank()) {
            this.subjects = new ArrayList<>();
        } else {
            this.subjects = Arrays.stream(subjectsConcat.split(","))
                    .map(String::trim)
                    .toList();
        }

        if (classesConcat == null || classesConcat.isBlank()) {
            this.classes = new ArrayList<>();
        } else {
            this.classes = Arrays.stream(classesConcat.split(","))
                    .map(String::trim)
                    .toList();
        }

        if (gradesConcat == null || gradesConcat.isBlank()) {
            this.grades = new ArrayList<>();
        } else {
            this.grades = Arrays.stream(gradesConcat.split(","))
                    .map(String::trim)
                    .toList();
        }
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getClasses() {
        return classes;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }
}
