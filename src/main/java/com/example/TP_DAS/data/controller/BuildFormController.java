package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.singleton.BuildRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BuildFormController {

    @GetMapping("/builds")
    @ResponseBody
    public String getBuildForm() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Build Submission</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <h1>Submit Build Request</h1>
                    <form id="build-form" action="/build" method="POST">
                        <div class="form-group">
                            <label for="projectId">Project ID:</label>
                            <input type="text" id="projectId" name="projectId" required>
                        </div>
                                           
                        <div class="form-group">
                            <label for="branchName">Branch Name:</label>
                            <input type="text" id="branchName" name="branchName" required>
                        </div>
                                           
                        <div class="form-group">
                            <label for="language">Language:</label>
                            <select id="language" name="language" required>
                                <option value="C">C</option>
                                <option value="C++">C++</option>
                            </select>
                        </div>
                                           
                        <div class="form-group">
                            <label for="sourceCode">Source Code:</label>
                            <textarea id="sourceCode" name="sourceCode" required></textarea>
                        </div>
                                           
                        <div class="form-group">
                            <label for="buildConfiguration">Build Configuration:</label>
                            <input type="text" id="buildConfiguration" name="buildConfiguration" placeholder="-O2">
                        </div>
                                           
                        <button type="submit">Submit Build</button>
                    </form>
                                           
                    <script src="script.js"></script>
                </body>
                </html>
                            """;
    }

    @PostMapping("/build")
    public String submitBuild(@ModelAttribute BuildRequest request) {
        // Submit the build request to the web API
       BuildController controller = new BuildController();
       controller.submitBuild(request);

        // Redirect to the build results page
        return "redirect:/build/{projectId}/results";
    }
}