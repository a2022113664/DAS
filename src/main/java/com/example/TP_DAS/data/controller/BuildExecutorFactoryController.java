package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.factory.BuildExecutor;
import com.example.TP_DAS.data.factory.CBuildExecutor;
import com.example.TP_DAS.data.factory.CPPBuildExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/builds/executors")
public class BuildExecutorFactoryController {

    @GetMapping("/{language}")
    public BuildExecutor getBuildExecutor(@PathVariable String language) {
        if (language.equalsIgnoreCase("C")) {
            return new CBuildExecutor();
        } else if (language.equalsIgnoreCase("C++")) {
            return new CPPBuildExecutor();
        } else {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
