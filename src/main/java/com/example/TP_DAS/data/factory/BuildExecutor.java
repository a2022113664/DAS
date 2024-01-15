package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.singleton.BuildRequest;
import com.example.TP_DAS.data.observer.BuildResult;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BuildExecutor {
    private final ExecutorService executorService;

    public BuildExecutor() {
        this(Executors.newFixedThreadPool(10));
    }
    public BuildExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public BuildResult executeBuild(BuildRequest request) throws InterruptedException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("gcc", "-Wall", "-o", request.getProjectId(), request.getSourceCode()); // Create the process builder instance

        Future<BuildResult> future = executorService.submit(() -> {
            Process process = null;
            try {
                // Start the build process
                process = processBuilder.start();
                System.out.println("Building project with ID: " + request.getProjectId());
                Thread.sleep(5000); // Simulate build execution time

                if (checkBuildSuccess(process)) {
                    return new BuildResult(request.getProjectId(), true, "Build successful");
                } else {
                    return new BuildResult(request.getProjectId(), false, "Build failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return new BuildResult(request.getProjectId(), false, "Build interrupted");
            } finally {
                // Cleanup the process
                if (process != null) {
                    process.destroy();
                }
            }
        });

        try {
            return future.get(); // Wait for the build to finish and return the result
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new BuildResult(request.getProjectId(), false, "Build interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new BuildResult(request.getProjectId(), false, "Build failed");
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private boolean checkBuildSuccess(Process process) {
        // Check for error messages in the build output
        String buildOutput = process.getInputStream().toString();
        if (buildOutput.contains("error")) {
            return false;
        }

        // Check the exit code of the build process
        int exitCode = process.exitValue();
        if (exitCode != 0) {
            return false;
        }

        // If both checks pass, the build is considered successful
        return true;
    }
}
