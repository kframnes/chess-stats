package com.framnes.chessstats.engine;

import com.google.inject.Singleton;

@Singleton
public class EngineFactory {

    private String enginePath;

    public EngineFactory(String enginePath) {
        this.enginePath = enginePath;
    }

    public Engine getEngine() {
        return new Engine(enginePath);
    }

}
