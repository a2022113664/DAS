package com.example.TP_DAS.data.singleton;

import com.example.TP_DAS.data.factory.BuildExecutor;
import com.example.TP_DAS.data.observer.BuildResult;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuildQueue {
    private static BuildQueue instance;
    private BlockingQueue <BuildRequest> buildQueue; // Fila - Array de instancias
    private BuildExecutor buildExecutor; // Declare the BuildExecutor instance
    private BuildResultSubject resultSubject; // Include an instance of BuildResultSubject

    public BuildQueue() {
        this.buildQueue = new ArrayBlockingQueue<>(10); // Max concurrent builds
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Fixed thread pool size
        this.buildExecutor = new BuildExecutor(executorService); // Instantiate your BuildExecutor
        this.resultSubject = new BuildResultSubject(); // Instantiate BuildResultSubject
    }
    @Autowired // Inject the BuildExecutor using Spring
    public static BuildQueue getInstance() {
        if (instance == null) {
            instance = new BuildQueue();
        }
        return instance;
    }
    public void addBuild(BuildRequest request) {
        try{
            this.buildQueue.put(request); // Adiciona o request/intancia à fila.
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BuildRequest getNextRequest() throws InterruptedException, IOException {
        if (this.buildQueue.isEmpty()) {
            return null;
        }
        BuildRequest request = this.buildQueue.poll(); // Retrieve the build request from the queue
        BuildResult result = buildExecutor.executeBuild(request);
        resultSubject.notifyObservers(result);
        return request;
    }
}