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
import java.util.ArrayList;
import java.util.List;
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
    public String getResultFieldMask(
            @PathVariable String projectId,
            @RequestParam(name = "fieldMask", required = false) List<String> fieldMaskStrings,
            Model model) {

        BuildResult result = buildQueue.getResults(projectId);

        if (result == null) {
            return "fieldMaskResultNotFound";
        }

        List<String> objectReturnList = new ArrayList<>();

        if (fieldMaskStrings != null && !fieldMaskStrings.isEmpty()) {
            for (String fieldMaskString : fieldMaskStrings) {
                String objectReturn = getField(result, fieldMaskString);
                if (objectReturn != null) {
                    objectReturnList.add(objectReturn);
                } else {
                    model.addAttribute("errorMessage",fieldMaskString);
                    return "fieldMaskError";
                }
            }
        } else {
            // Se nenhum fieldMask for fornecido, retorna todos os campos, incluindo a mensagem
            objectReturnList.add(getField(result, "projectId"));
            objectReturnList.add(getField(result, "message"));
            objectReturnList.add(getField(result, "success"));
        }

        model.addAttribute("resultFields", objectReturnList);

        return "fieldMaskResult";
    }

    public String getField(BuildResult result, String fieldName){
        switch (fieldName) {
            case "projectId":
                return  "<b>ProjectId:</b> " + result.getProjectId() + "<br>";
            case "message":
                return  "<b>Message:</b> " + result.getMessage() + "<br>";
            case "success":
                return "<b>Success:</b> " + String.valueOf(result.isSuccess())  + "<br>";
            default:
                return null;
        }
    }

}
