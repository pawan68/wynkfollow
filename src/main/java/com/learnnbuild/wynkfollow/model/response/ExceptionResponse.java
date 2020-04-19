package com.learnnbuild.wynkfollow.model.response;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {
    private String status;
    private String message;

    public ExceptionResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
