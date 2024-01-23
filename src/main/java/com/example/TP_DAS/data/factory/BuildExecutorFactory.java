package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.model.interfaces.BuildExecutor;
import com.example.TP_DAS.model.interfaces.BuildExecutorType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BuildExecutorFactory {

    private static final Map<BuildExecutorType, BuildExecutorFactory> executorFactories = new HashMap<>();

    public static BuildExecutorFactory getExecutorFactory(BuildExecutorType type) {
        if (!executorFactories.containsKey(type)) {
            throw new IllegalArgumentException("No executor factory found for type: " + type);
        }
        return executorFactories.get(type);
    }

    public BuildExecutor getBuildExecutor(BuildRequest request) throws IOException, InterruptedException {
        // Create the appropriate executor based on the requested language
        BuildExecutorType executorType = getExecutorType(request.getLanguage());
        BuildExecutor executor = executorType.createExecutor();
        executor.configure(request.getProjectId(), request.getLanguage(), request.getBuildConfiguration(), request.getSourceCodeFile());
        executor.executeBuild(request);
        return executor;
    }

    private BuildExecutorType getExecutorType(String language) {
        switch (language) {
            case "C":
                return new CCompilerExecutorType();
            case "C++":
                return new CPlusPlusCompilerExecutorType();
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}