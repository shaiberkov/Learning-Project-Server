package org.example.learningprojectserver.response;

public class TokenValidationResponse extends BasicResponse{

        private boolean isValid;
        private String username;

        public TokenValidationResponse(boolean success, String error, boolean isValid,String username) {
            super(success, error);
            this.isValid = isValid;
            this.username = username;
        }

    public TokenValidationResponse() {
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
