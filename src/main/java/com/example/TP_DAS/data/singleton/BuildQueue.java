package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.data.BuildRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BuildQueue {

    private static BuildQueue instance = new BuildQueue();

    private BlockingQueue<BuildTask> queue = new LinkedBlockingQueue<>();

    private List<BuildRequest> completedBuilds;

    public BuildQueue() {
        completedBuilds = new ArrayList<>();
    }

    public static BuildQueue getInstance() {
        if (instance == null) {
            instance = new BuildQueue();
        }
        return instance;

    }

    public void add(BuildTask task) {
        queue.add(task);
    }

    public BuildTask poll() {
        return queue.poll();
    }

    public void completeBuild(BuildRequest request) {
        completedBuilds.add(request);
    }

    public List<BuildRequest> getCompletedBuilds() {
        return completedBuilds;
    }
}