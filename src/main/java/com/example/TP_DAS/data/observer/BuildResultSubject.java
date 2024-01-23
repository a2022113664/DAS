package com.example.TP_DAS.data.observer;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.model.interfaces.BuildResultObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildResultSubject {

    private List<BuildResultObserver> observers = new ArrayList<>();

    private List<BuildResult> results;


    public BuildResultSubject(){
        results = new ArrayList<>();
    }
    public void addBuildResultObserver(BuildResultObserver observer) {
        observers.add(observer);
    }

    public void removeBuildResultObserver(BuildResultObserver observer) {
        observers.remove(observer);
    }

    public void publishBuildResult(BuildResult result) {
        results.add(result);

        for (BuildResultObserver observer : observers) {
            observer.updateBuildResult(result);
        }
    }

    public BuildResult getBuildResult(String projectId) {
        for (BuildResult result : results) {
            if (result.getProjectId().equals(projectId)) {
                return result;
            }
        }
        return null;
    }
}
