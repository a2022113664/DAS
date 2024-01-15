package com.example.TP_DAS.data.observer;

import com.example.TP_DAS.model.interfaces.BuildResultObserver;

import java.util.ArrayList;
import java.util.List;

public class BuildResultSubject {
    private List<BuildResultObserver> observers = new ArrayList<>();

    public void addObserver(BuildResultObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BuildResultObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(BuildResult result) {
        for (BuildResultObserver observer : observers) {
            observer.update(result);
        }
    }
}