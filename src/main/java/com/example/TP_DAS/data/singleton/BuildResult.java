package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.model.interfaces.BuildResultInterface;

public class BuildResult implements BuildResultInterface {
    private String projectId;
    private boolean success;
    private String message;

    public BuildResult(String projectId, boolean success, String message) {
        this.projectId = projectId;
        this.success = success;
        this.message = message;
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
