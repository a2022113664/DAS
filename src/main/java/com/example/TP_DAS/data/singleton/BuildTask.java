package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.model.interfaces.BuildExecutor;

import java.io.IOException;

public class BuildTask implements Runnable {

    private BuildExecutor executor;
    private BuildRequest request;

    public BuildTask(BuildExecutor executor, BuildRequest request) {
        this.executor = executor;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            // Execute the build using the configured executor
            executor.executeBuild(request);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}