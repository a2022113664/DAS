package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.data.factory.BuildExecutorFactory;
import com.example.TP_DAS.data.singleton.BuildQueue;
import com.example.TP_DAS.model.interfaces.BuildExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

@Controller
@RequestMapping("/builds")
public class BuildController {

    private BuildQueue buildQueue;

    public BuildController() {
        this.buildQueue = BuildQueue.getInstance();
    }

    @PostMapping
    public void submitBuild(@RequestBody BuildRequest request) {

        try {
            buildQueue.add(request);
            BlockingQueue<BuildRequest> allQueues = buildQueue.getQueue();
            for (BuildRequest queue : allQueues) {
                buildQueue.executeCompilation(queue);
                buildQueue.completeBuild(request);
                buildQueue.poll();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{projectId}/remove")
    public String removeBuild(@PathVariable String projectId) {
        buildQueue.removeBuild(projectId);
        return "redirect:/builds/list";
    }


    @GetMapping("/{projectId}/results")
    public String getResult(@PathVariable String projectId, Model model) {
        BuildResult  result = buildQueue.getResults(projectId);
        model.addAttribute("result", result);

        return "buildResult";
    }

    @GetMapping("/{projectId}/results/get")
    public ResponseEntity<String> getResultFieldMask(@PathVariable String projectId, @RequestParam(name = "fieldMask", required = false) String fieldMaskString) {
        BuildResult  result = buildQueue.getResults(projectId);

        String objectReturn = getField(result, fieldMaskString);

        return ResponseEntity.ok(objectReturn);
    }


    public String getField(BuildResult result, String fieldName){
        switch (fieldName) {
            case "projectId":
                return result.getProjectId();
            case "message":
                return result.getMessage();
            default:
                return null;
        }
    }

}
