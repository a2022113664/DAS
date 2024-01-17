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

public class CCompilerExecutor implements BuildExecutor {

    private String compilerPath;
    private List<String> compilerArgs = new ArrayList<>();
    private List<String> sourceFilePaths;
    private String outputFilePath;

    public void configure(String projectId, String language, String buildConfiguration, File sourceCodeFile) {
        // Extract compiler path from system environment variables
        compilerPath = System.getenv("CC");

        // Check if the source code file is provided
        if (sourceCodeFile != null) {
            // Initialize the list of source code file paths
            sourceFilePaths = Arrays.asList(sourceCodeFile.getAbsolutePath());

            // Extract the source code file path
            String sourceCodeFilePath = sourceCodeFile.getAbsolutePath();

            // Add compiler flags based on build configuration
            if (buildConfiguration != null && buildConfiguration.equals("debug")) {
                compilerArgs.add("-g");
            }

            // Convert the list of relative file paths to absolute paths
            List<String> absoluteSourceFilePaths = new ArrayList<>();
            for (String filePath : sourceFilePaths) {
                absoluteSourceFilePaths.add(new File(sourceCodeFilePath).toPath().resolve(filePath).toAbsolutePath().toString());
            }

            // Specify the output file path
            outputFilePath = projectId + ".out";
        } else {
            // Handle the case where source code file is not provided
            System.out.println("Error: Source code file not specified.");
        }
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
