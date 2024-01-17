package com.example.TP_DAS.data.singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BuildQueue {

    private static BuildQueue instance = new BuildQueue();

    private BlockingQueue<BuildTask> queue = new LinkedBlockingQueue<>();

    public BuildQueue() {
    }

    public static BuildQueue getInstance() {
        return instance;
    }

    public void add(BuildTask task) {
        queue.add(task);
    }

    public BuildTask poll() {
        return queue.poll();
    }
}