package com.example.TP_DAS.data;

import java.io.File;

public class BuildRequest {

    private String projectId;
    private String language;
    private File sourceCodeFile;
    private String buildConfiguration;

    public BuildRequest(String projectId, String language, String buildConfiguration, File sourceCodeFile) {
        this.projectId = projectId;
        this.language = language;
        this.buildConfiguration = buildConfiguration;
        this.sourceCodeFile = sourceCodeFile;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public File getSourceCodeFile() {
        if (sourceCodeFile == null) {
            throw new NullPointerException("Source code file must be specified");
        }

        return sourceCodeFile;
    }

    public void setSourceCodeFile(File sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
    }

    public String getBuildConfiguration() {
        return buildConfiguration;
    }

    public void setBuildConfiguration(String buildConfiguration) {
        this.buildConfiguration = buildConfiguration;
    }
}
