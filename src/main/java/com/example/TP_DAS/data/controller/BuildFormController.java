package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/builds")
public class BuildFormController {

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/build")
    public String submitBuild(@ModelAttribute BuildRequest request) {
        // Submit the build request to the web API

       BuildController controller = new BuildController();
       controller.submitBuild(request);

        // Redirect to the build results page
        return "redirect:/build/" + request.getProjectId() + "/results";
    }
}