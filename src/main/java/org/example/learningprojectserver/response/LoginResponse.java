package org.example.learningprojectserver.response;

public class LoginResponse extends BasicResponse {
    private String token;


    public LoginResponse() {

    }

    public LoginResponse(boolean success, String errorCode, String token) {
        super(success, errorCode);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
