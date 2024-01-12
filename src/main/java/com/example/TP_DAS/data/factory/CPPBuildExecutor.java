package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.singleton.BuildRequest;
import com.example.TP_DAS.data.singleton.BuildResult;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

public class CPPBuildExecutor extends BuildExecutor {
    public CPPBuildExecutor() {
        super(); // Initialize the base class constructor
    }

    public CPPBuildExecutor(ExecutorService executorService) {
        super(executorService); // Initialize the base class constructor and pass the executor service
    }

    // Override the executeBuild method to perform C++ compilation
    @Override
    public BuildResult executeBuild(BuildRequest request) throws InterruptedException, IOException {
        // Create a ProcessBuilder for the C++ compiler
        ProcessBuilder builder = new ProcessBuilder("g++", "-Wall", "-o", request.getProjectId(), request.getSourceCode());

        // Start the build process and wait for it to finish
        Process process = builder.start();
        process.waitFor();

        // Check the exit code to determine build success or failure
        int exitCode = process.exitValue();
        if (exitCode == 0) {
            return new BuildResult(request.getProjectId(), true, "C++ build successful");
        } else {
            // Check for specific errors related to missing headers or undefined symbols
            String errorOutput = process.getErrorStream().toString();
            Pattern headerErrorPattern = Pattern.compile("cannot find header file");
            Pattern undefinedSymbolPattern = Pattern.compile("undefined reference to");

            if (headerErrorPattern.matcher(errorOutput).find()) {
                return new BuildResult(request.getProjectId(), false, "C++ build failed: Missing header files");
            } else if (undefinedSymbolPattern.matcher(errorOutput).find()) {
                return new BuildResult(request.getProjectId(), false, "C++ build failed: Undefined symbols");
            } else {
                return new BuildResult(request.getProjectId(), false, "C++ build failed");
            }
        }
    }
}
