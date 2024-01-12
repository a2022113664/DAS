package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.singleton.BuildRequest;
import com.example.TP_DAS.data.singleton.BuildResult;

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
        Future<BuildResult> future = executorService.submit(() -> {
            try {
                // Perform the actual build process
                System.out.println("Building project with ID: " + request.getProjectId());
                Thread.sleep(5000); // Simulate build execution time

                if (checkBuildSuccess()) {
                    return new BuildResult(request.getProjectId(), true, "Build successful");
                } else {
                    return new BuildResult(request.getProjectId(), false, "Build failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return new BuildResult(request.getProjectId(), false, "Build interrupted");
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

    private boolean checkBuildSuccess() {
        // Implement logic to check if the build was successful
        // For example, you could check if the build output contains any errors
        return true;
    }
}
