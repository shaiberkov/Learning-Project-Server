package org.example.learningprojectserver.response;

public class TokenValidationResponse extends BasicResponse{

        private boolean isValid;
        private String userId;
        private String username;
        private String role;
        private String SchoolCode;

        public TokenValidationResponse(boolean success, String error, boolean isValid,String username
        ) {
            super(success, error);
            this.isValid = isValid;
            this.username = username;
        }

    public String getSchoolCode() {
        return SchoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        SchoolCode = schoolCode;
    }

    public TokenValidationResponse() {

        }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "TokenValidationResponse{" +
                "isValid=" + isValid +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
