package org.example.learningprojectserver.notification.dto;

import org.example.learningprojectserver.dto.StudentTestStatusDTO;

public class NewTestMessageDTO {
    private StudentTestStatusDTO studentTestStatusDTO;

    public NewTestMessageDTO(StudentTestStatusDTO studentTestStatusDTO) {
        this.studentTestStatusDTO = studentTestStatusDTO;
    }

    public NewTestMessageDTO() {
    }

    public StudentTestStatusDTO getUserTestStatusDTO() {
        return studentTestStatusDTO;
    }

    public void setUserTestStatusDTO(StudentTestStatusDTO studentTestStatusDTO) {
        this.studentTestStatusDTO = studentTestStatusDTO;
    }
}
