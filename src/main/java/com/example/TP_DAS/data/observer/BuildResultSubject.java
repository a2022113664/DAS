package com.example.TP_DAS.data.observer;

import com.example.TP_DAS.data.singleton.BuildQueue;
import com.example.TP_DAS.data.singleton.BuildRequest;
import com.example.TP_DAS.model.interfaces.BuildResultObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildResultSubject {

    private List<BuildResultObserver> observers;
    private final BuildQueue buildQueue;
    private Map<String, BuildResult> completedBuilds;

    public BuildResultSubject() {
        this.buildQueue = new BuildQueue();
        this.observers = new ArrayList<>();
        this.completedBuilds = new HashMap<>();
    }

    public void addObserver(BuildResultObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BuildResultObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(BuildResult result) {
        for (BuildResultObserver observer : observers) {
            observer.update(result);
        }
    }

    public BuildResult getBuildResult(String projectId) throws InterruptedException, IOException {
        BuildRequest request = buildQueue.getNextRequest();
        while (request != null) {
            if (request.getProjectId().equals(projectId)) {
                BuildResult buildResult = new BuildResult(request.getProjectId(), true, "Build completed");
                completedBuilds.put(projectId, buildResult);
                return buildResult;
            }
            request = buildQueue.getNextRequest();
        }

        if (completedBuilds.containsKey(projectId)) {
            return completedBuilds.get(projectId);
        }

        return null;
    }

}