package com.example.TP_DAS.model.interfaces;

import com.example.TP_DAS.data.BuildResult;

public interface BuildResultObserver {
    void updateBuildResult(BuildResult result);
}
