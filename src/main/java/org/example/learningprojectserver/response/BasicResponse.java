package org.example.learningprojectserver.response;


public class BasicResponse<T>{
    private boolean success;
    private String errorCode;
    private T data;
    public BasicResponse(boolean success, String errorCode) {
        this.success = success;
        this.errorCode = errorCode;



    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", data=" + data +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

}