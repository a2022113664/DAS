package com.example.TP_DAS.data.observer;

import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.model.interfaces.BuildResultObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildResultSubject {

    private List<BuildResultObserver> observers = new ArrayList<>();

    private Map<String, BuildResult> results = new HashMap<>();

    public void addBuildResultObserver(BuildResultObserver observer) {
        observers.add(observer);
    }

    public void removeBuildResultObserver(BuildResultObserver observer) {
        observers.remove(observer);
    }

    public void publishBuildResult(BuildResult result) {
        results.put(result.getProjectId(), result);

        for (BuildResultObserver observer : observers) {
            observer.updateBuildResult(result);
        }
    }

    public BuildResult getBuildResult(String projectId) {
        return results.get(projectId);
    }
}
