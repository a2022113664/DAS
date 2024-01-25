package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.data.BuildRequest;
import com.example.TP_DAS.data.BuildResult;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import com.example.TP_DAS.data.singleton.BuildQueue;
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
    private BuildQueue buildQueue;


    public CPlusPlusCompilerExecutor() {
        this.buildQueue = BuildQueue.getInstance();
    }

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
            outputFilePath = projectId;
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

            ProcessBuilder builder = new ProcessBuilder(commandList);
            builder.directory(new File("."));

            Process process = builder.start();
            String outputError = new String(process.getErrorStream().readAllBytes());
            process.waitFor();
            BuildResult result;
            if(outputError.equals("")){
                result = new BuildResult(request.getProjectId(), true, outputError);
            }else{
                result = new BuildResult(request.getProjectId(), false, outputError);
            }
            this.buildQueue.addResult(result);

        } else {
            System.out.println("Error: Compiler path is not specified.");
        }
    }
}
