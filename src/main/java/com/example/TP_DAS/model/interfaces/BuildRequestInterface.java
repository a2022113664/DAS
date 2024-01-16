package com.example.TP_DAS.model.interfaces;

public interface BuildRequestInterface {
    String getProjectId();
    String getSourceCode();
    String getBuildConfiguration();

    String getLanguage();
}
