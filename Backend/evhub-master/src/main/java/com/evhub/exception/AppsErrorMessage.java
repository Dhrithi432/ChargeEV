package com.evhub.exception;

import java.io.Serializable;

public class AppsErrorMessage implements Serializable {

    private static final long serialVersionUID = -3720848959116729056L;

    private String errorMessage;

    public AppsErrorMessage(String error) {
        this.errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
