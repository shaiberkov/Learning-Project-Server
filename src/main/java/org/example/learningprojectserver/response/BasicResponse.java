package org.example.learningprojectserver.response;


public class BasicResponse {
    private boolean success;
    private String errorCode;

    public BasicResponse(boolean success, String errorCode) {
        this.success = success;
        this.errorCode = errorCode;
    }

    public BasicResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}