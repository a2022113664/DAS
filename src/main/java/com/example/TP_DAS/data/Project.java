package com.example.TP_DAS.data;

public class Project {

    private String projectId;
    private String name;
    private String description;
    private String branchName;

    public Project(String projectId, String name, String description, String branchName) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.branchName = branchName;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBranchName() {
        return branchName;
    }
}