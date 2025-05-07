package org.example.learningprojectserver.dto;

public class StudentDTO {
    private String fullName;
    private String phone;
    private String className;
    private String gradeName;
    public StudentDTO() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", className='" + className + '\'' +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
