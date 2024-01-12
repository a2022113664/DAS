package com.example.TP_DAS.data.factory;

public class BuildExecutorFactory {
    public static BuildExecutor getBuildExecutor(String language) {
        if (language.equalsIgnoreCase("C")) {
            return new CBuildExecutor();
        } else if (language.equalsIgnoreCase("C++")) {
            return new CPPBuildExecutor();
        } else {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
