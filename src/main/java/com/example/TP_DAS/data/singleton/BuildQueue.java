package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.data.observer.BuildResultSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BuildQueue {

    private static BuildQueue instance = new BuildQueue();

    private BlockingQueue<BuildRequest> queue = new LinkedBlockingQueue<>();

    private List<BuildRequest> completedBuilds;

    private BuildResultSubject buildResultSubject;


    public BuildQueue() {
        completedBuilds = new ArrayList<>();
        this.buildResultSubject = new BuildResultSubject();

    }

    public static BuildQueue getInstance() {
        if (instance == null) {
            instance = new BuildQueue();
        }
        return instance;

    }

    public void add(BuildRequest request) {
        queue.add(request);
    }

    public BuildRequest poll() {
        return queue.poll();
    }

    public void completeBuild(BuildRequest request) {
        completedBuilds.add(request);
    }

    public List<BuildRequest> getCompletedBuilds() {
        return completedBuilds;
    }

    public void addResult(BuildResult result){
        this.buildResultSubject.publishBuildResult(result);
    }

    public BuildResult getResults(String projectId){
      return buildResultSubject.getBuildResult(projectId);
    }
}