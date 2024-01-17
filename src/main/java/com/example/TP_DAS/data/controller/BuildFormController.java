package com.example.TP_DAS.data.controller;

import com.example.TP_DAS.data.BuildRequest;
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
                         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
                         <link rel="stylesheet" href="style.css">
                       </head>
                       <body>
                         <div class="container">
                           <h1 class="h2 text-center my-4">Submit Build Request</h1>
                       
                           <form id="build-form" class="needs-validation" action="/build" method="POST">
                             <div class="row mb-3">
                               <label for="projectId" class="col-sm-2 col-form-label">Project ID:</label>
                               <div class="col-sm-10">
                                 <input type="text" id="projectId" name="projectId" required class="form-control" placeholder="Enter project ID">
                                 <div class="invalid-feedback">Please enter a valid project ID.</div>
                               </div>
                             </div>
                       
                             <div class="row mb-3">
                               <label for="language" class="col-sm-2 col-form-label">Language:</label>
                               <div class="col-sm-10">
                                 <select id="language" name="language" required class="form-select">
                                   <option value="C">C</option>
                                   <option value="C++">C++</option>
                                 </select>
                                 <div class="invalid-feedback">Please select a valid language.</div>
                               </div>
                             </div>
                       
                             <div class="row mb-3">
                               <label for="sourceCode" class="col-sm-2 col-form-label">Source Code File:</label>
                               <div class="col-sm-10">
                                 <input type="file" id="sourceCodeFile" name="sourceCodeFile" required>
                               </div>
                             </div>
                       
                             <div class="row mb-3">
                               <label for="buildConfiguration" class="col-sm-2 col-form-label">Build Configuration:</label>
                               <div class="col-sm-10">
                                 <input type="text" id="buildConfiguration" name="buildConfiguration" class="form-control" placeholder="-O2">
                               </div>
                             </div>
                       
                             <div class="d-flex justify-content-between my-4">
                               <button type="submit" class="btn btn-primary">Submit Build</button>
                               <a href="#" class="btn btn-secondary">Cancel</a>
                             </div>
                           </form>
                         </div>
                       
                         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
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
        return "redirect:/build/" + request.getProjectId() + "/results";
    }
}