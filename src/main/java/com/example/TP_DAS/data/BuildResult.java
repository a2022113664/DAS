package com.example.TP_DAS.data;

public class BuildResult {

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
