package com.evhub.model.common;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsErrorMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 8412716094442530250L;

    private List<AppsErrorMessage> appsErrorMessages = new ArrayList<>();

    private T result;

    private AppsConstants.ResponseStatus status;

    public ResponseDTO() {
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public AppsConstants.ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.ResponseStatus status) {
        this.status = status;
    }
}
