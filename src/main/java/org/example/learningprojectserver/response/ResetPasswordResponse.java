package org.example.learningprojectserver.response;

public class ResetPasswordResponse extends BasicResponse{

    private String userNameError;
    private String passwordError;
    private String otpError;

    public ResetPasswordResponse(){

    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setOtpError(String otpError) {
        this.otpError = otpError;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getOtpError() {
        return otpError;
    }
}
