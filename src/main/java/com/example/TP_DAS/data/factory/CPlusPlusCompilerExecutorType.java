package com.example.TP_DAS.data.factory;

import com.example.TP_DAS.model.interfaces.BuildExecutor;
import com.example.TP_DAS.model.interfaces.BuildExecutorType;

public class CPlusPlusCompilerExecutorType implements BuildExecutorType {

    @Override
    public BuildExecutor createExecutor() {
        return new CPlusPlusCompilerExecutor();
    }
}
