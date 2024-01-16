package com.example.TP_DAS;

import com.example.TP_DAS.data.factory.BuildExecutor;
import com.example.TP_DAS.data.observer.BuildResultSubject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildQueueConfiguration {

    @Bean
    public BuildExecutor buildExecutor() {
        return new BuildExecutor();
    }

    @Bean
    public BuildResultSubject resultSubject() {
        return new BuildResultSubject();
    }
}