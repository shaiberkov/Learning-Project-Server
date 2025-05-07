package org.example.learningprojectserver.dto;

import java.util.List;

public class TeacherDTO {

    private String teacherName;
    private String email;
    private String phone;
    private List<String> subjects;      // מקצועות שהמורה מלמד
    private List<String> classes;
    private List<String> grades;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

    public TeacherDTO() {}

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "teacherName='" + teacherName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", subjects=" + subjects +
                ", classes=" + classes +
                ", grades=" + grades +
                '}';
    }
}
