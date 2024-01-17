package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.model.interfaces.BuildExecutor;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CPlusPlusCompilerExecutor implements BuildExecutor {

    private String compilerPath;
    private List<String> compilerArgs = new ArrayList<>();
    private List<String> sourceFilePaths;
    private String outputFilePath;

    public void configure(String projectId, String language, String buildConfiguration, File sourceCodeFile) {
        // Extract compiler path from system environment variables
      //  compilerPath = System.getenv("CPP");

        // Add compiler flags based on build configuration
       // if (request.getBuildConfiguration().equals("debug")) {
       //     compilerArgs.add("-g");
       // }

        // Get the source code file paths from the request
        //sourceFilePaths = Arrays.asList(request.getSourceCodeFile().getAbsolutePath());

        // Specify the output file path
       // outputFilePath = request.getProjectId() + ".out";
    }

    @Override
    public void executeBuild(BuildRequest request) throws IOException, InterruptedException {
        // Build the source code files into an executable file
        String compilerCommand = String.join(" ", compilerArgs);
        ProcessBuilder builder = new ProcessBuilder().command(compilerPath, compilerCommand);
        builder.directory(new File("."));

        // Execute the compiler as a subprocess
        Process process = builder.start();

        // Read and discard the compiler's output
        try (InputStream inputStream = process.getInputStream()) {
            IOUtils.copy(inputStream, System.out);
        }

        // Wait for the compiler to finish
        process.waitFor();
    }
}
