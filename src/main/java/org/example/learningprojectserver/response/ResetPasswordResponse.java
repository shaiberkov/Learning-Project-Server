package org.example.learningprojectserver.response;

public class ResetPasswordResponse extends BasicResponse{

    private String userIdError;
    private String passwordError;
    private String otpError;

    public ResetPasswordResponse(){

    }

    public void setUserIdError(String userIdError) {
        this.userIdError = userIdError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setOtpError(String otpError) {
        this.otpError = otpError;
    }

    public String getUserIdError() {
        return userIdError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getOtpError() {
        return otpError;
    }
}
