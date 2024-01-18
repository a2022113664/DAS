package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.model.interfaces.BuildExecutor;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
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
        compilerPath = System.getenv("CC");;

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
        if (compilerPath != null) {
            // Construct the command line
            String compilerCommand = "g++";
            String sourceFilePath = sourceFilePaths.get(0);

            // Add individual elements to the command list
            List<String> commandList = new ArrayList<>();
            commandList.add(compilerCommand);

            // Check if compilerArgs is not empty
            if (!compilerArgs.isEmpty()) {
                commandList.addAll(compilerArgs);
            }

            commandList.add(sourceFilePath);
            commandList.add("-o");
            commandList.add(outputFilePath);

            ProcessBuilder builder = new ProcessBuilder().command(commandList);
            builder.directory(new File("."));

            try {
                Process process = builder.start();

                // Capture and handle compiler output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Wait for the compiler to finish
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Compiler path is not specified.");
        }
    }
}
