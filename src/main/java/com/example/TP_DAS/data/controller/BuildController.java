package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.factory.BuildExecutorFactory;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import com.example.TP_DAS.data.singleton.BuildQueue;
import com.example.TP_DAS.data.singleton.BuildTask;
import com.example.TP_DAS.model.interfaces.BuildExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
//@RequestMapping("/builds")
public class BuildController {

    private BuildQueue buildQueue;
    private BuildResultSubject resultSubject;
    private BuildExecutorFactory executorFactory;

    public BuildController() {
        this.buildQueue = BuildQueue.getInstance();
        this.resultSubject = new BuildResultSubject();
        this.executorFactory = new BuildExecutorFactory();
    }

    @PostMapping
    public void submitBuild(@RequestBody BuildRequest request) {
        try {

            // Create the appropriate executor based on the requested language
            BuildExecutor executor = this.executorFactory.getBuildExecutor(request);

            // Submit the build request to the executor and add it to the queue
            buildQueue.add(new BuildTask(executor, request));
            buildQueue.completeBuild(request);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
