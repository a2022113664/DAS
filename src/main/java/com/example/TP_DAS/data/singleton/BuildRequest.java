package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.model.interfaces.BuildRequestInterface;

public class BuildRequest implements BuildRequestInterface {
    private String projectId;
    private String sourceCode;
    private String buildConfiguration;
    private String buildOutput;

    public BuildRequest(String projectId, String sourceCode, String buildConfiguration) {
        this.projectId = projectId;
        this.sourceCode = sourceCode;
        this.buildConfiguration = buildConfiguration;
    }

    @Override
    public String getProjectId() {
        return this.projectId;
    }

    @Override
    public String getSourceCode() {
        return this.sourceCode;
    }

    @Override
    public String getBuildConfiguration() {
        return this.buildConfiguration;
    }

    public String getBuildOutput() {
        return buildOutput;
    }

    // Method to set the build output
    public void setBuildOutput(String buildOutput) {
        this.buildOutput = buildOutput;
    }
}
