package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
//@RequestMapping("/build/{projectId}/results")
public class BuildResultController {

    private BuildResultSubject resultSubject;

    public BuildResultController() {
        this.resultSubject = new BuildResultSubject();
    }

    //@GetMapping("/{projectId}/results")
    public BuildResult getBuildResult(@PathVariable String projectId) {
        BuildResult result = resultSubject.getBuildResult(projectId);
        if (result == null) {
            return new BuildResult(projectId, false, "Build result not found");
        }
        return result;
    }
}