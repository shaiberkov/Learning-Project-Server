package org.example.learningprojectserver.dto;


public class SchoolDTO {

    private String schoolName;
    private String schoolCode;
    private int teacherCount;
    private int studentCount;

    public int getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(int teacherCount) {
        this.teacherCount = teacherCount;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public SchoolDTO() {
    }

    @Override
    public String toString() {
        return "SchoolDTO{" +
                "schoolName='" + schoolName + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                '}';
    }
}
