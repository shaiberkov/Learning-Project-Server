package org.example.learningprojectserver.response;

public class RegisterResponse extends BasicResponse{

    private String message;
    private String usernameTaken;
    private String phoneTaken;
    private String emailTaken;


    public RegisterResponse(boolean success, String errorCode, String message, String usernameTaken, String phoneTaken, String emailTaken) {
        super(success, errorCode);
        this.message = message;
        this.usernameTaken = usernameTaken;
        this.phoneTaken = phoneTaken;
        this.emailTaken = emailTaken;
    }

    public RegisterResponse() {
    }

    public String getMassage() {
        return message;
    }

    public String getUsernameTaken() {
        return usernameTaken;
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

    public void setUsernameTaken(String usernameTaken) {
        this.usernameTaken = usernameTaken;
    }

    public void setPhoneTaken(String phoneTaken) {
        this.phoneTaken = phoneTaken;
    }

    public void setEmailTaken(String emailTaken) {
        this.emailTaken = emailTaken;
    }
}