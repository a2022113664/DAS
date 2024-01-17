package com.example.TP_DAS.model.interfaces;

import com.example.TP_DAS.data.BuildRequest;

import java.io.File;
import java.io.IOException;

public interface BuildExecutor {

    void executeBuild(BuildRequest request) throws IOException, InterruptedException;

    void configure(String projectId, String language, String buildConfiguration, File sourceCodeFile);
}
