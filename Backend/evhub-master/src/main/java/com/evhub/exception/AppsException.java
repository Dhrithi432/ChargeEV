package com.evhub.exception;

import java.util.ArrayList;
import java.util.List;

public class AppsException extends Exception {

    private static final long serialVersionUID = -3187164646066610567L;

    private List<AppsErrorMessage> appsErrorMessages = new ArrayList<>();

    public AppsException() {
    }

    public AppsException(String error) {
        this.appsErrorMessages.add(new AppsErrorMessage(error));
    }

    public List<AppsErrorMessage> getAppsErrorMessages() {
        if (appsErrorMessages == null) {
            appsErrorMessages = new ArrayList<>();
        }
        return appsErrorMessages;
    }

    public void setAppsErrorMessages(List<AppsErrorMessage> appsErrorMessages) {
        this.appsErrorMessages = appsErrorMessages;
    }
}
