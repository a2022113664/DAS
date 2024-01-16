package com.example.TP_DAS.data.singleton;


import com.example.TP_DAS.data.factory.BuildExecutor;
import com.example.TP_DAS.data.observer.BuildResult;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.*;

@Service
public class BuildQueue {

    private static volatile BuildQueue instance;

    @Autowired
    private BuildExecutor buildExecutor;

    @Autowired
    private BuildResultSubject resultSubject;

    private BlockingQueue<BuildRequest> queue;

    @PostConstruct
    public void init() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public synchronized static BuildQueue getInstance() {
        if (instance == null) {
            instance = new BuildQueue();
        }
        return instance;
    }

    public synchronized void addBuild(BuildRequest request) {
        try {
            this.queue.put(request); // Add the request to the queue
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized BuildRequest getNextRequest() throws InterruptedException, IOException {
        if (this.queue.isEmpty()) {
            return null;
        }
        BuildRequest request = this.queue.take(); // Retrieve the first available request
        BuildResult result = buildExecutor.executeBuild(request);
        resultSubject.notifyObservers(result);
        return request;
    }

}