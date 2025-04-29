package org.example.learningprojectserver.response;

public class RegisterResponse extends BasicResponse{

    private String message;
    private String idTaken;
    private String phoneTaken;
    private String emailTaken;
    private String passwordDontMatch;


    public RegisterResponse(boolean success, String errorCode, String message,String idTaken, String phoneTaken, String emailTaken, String passwordDontMatch) {
        super(success, errorCode);
        this.message = message;
        this.idTaken = idTaken;
        this.phoneTaken = phoneTaken;
        this.emailTaken = emailTaken;
        this.passwordDontMatch = passwordDontMatch;
    }

    public void setPasswordDontMatch(String passwordDontMatch) {
        this.passwordDontMatch = passwordDontMatch;
    }

    public String getPasswordDontMatch() {
        return passwordDontMatch;
    }

    public RegisterResponse() {
    }

    public String getMessage() {
        return message;
    }

    public String getIdTaken() {
        return idTaken;
    }

    public void setIdTaken(String idTaken) {
        this.idTaken = idTaken;
    }

    public String getMassage() {
        return message;
    }

    public String getEmailTaken() {
        return emailTaken;
    }

    public String getPhoneTaken() {
        return phoneTaken;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhoneTaken(String phoneTaken) {
        this.phoneTaken = phoneTaken;
    }

    public void setEmailTaken(String emailTaken) {
        this.emailTaken = emailTaken;
    }
}