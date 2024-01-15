package com.example.TP_DAS.data.observer;

import com.example.TP_DAS.data.observer.BuildResultSubject;
import com.example.TP_DAS.model.interfaces.BuildResultInterface;

public class BuildResult extends BuildResultSubject implements BuildResultInterface {
    private String projectId;
    private boolean success;
    private String message;

    public BuildResult(String projectId, boolean success, String message) {
        this.projectId = projectId;
        this.success = success;
        this.message = message;
        notifyObservers(this);
    }

    public String getProjectId() {
        return projectId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
