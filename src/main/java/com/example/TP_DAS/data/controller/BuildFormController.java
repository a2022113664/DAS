package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.singleton.BuildQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/builds")
public class BuildFormController {
    private BuildQueue allBuilds;

    public BuildFormController(){
        this.allBuilds = BuildQueue.getInstance();
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/create")
    public String create(Model model) {
        return "createBuild";
    }

    @PostMapping("/compileBuild")
    public String submitBuild(@ModelAttribute BuildRequest request) {
        // Submit the build request to the web API

       BuildController controller = new BuildController();
       controller.submitBuild(request);

        return "redirect:/builds/list";
    }

    @GetMapping("/list")
    public String getAllBuilds(Model model) {
        List<BuildRequest> allCompiledBuilds = allBuilds.getCompletedBuilds();
        model.addAttribute("allBuilds", allCompiledBuilds);

        return "buildsList";
    }

    // Foi para testar a página
    @GetMapping("/result")
    public String result(Model model) {
        return "buildResult";
    }
}