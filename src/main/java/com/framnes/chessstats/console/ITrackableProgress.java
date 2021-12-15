package com.framnes.chessstats.console;

/**
 * Interface for workers that have trackable progress.
 */
public interface ITrackableProgress {

    /**
     * @return how the worker should be labeled in list of workers in progress.
     */
    String trackableName();

    /**
     * @return the completed number of work units.
     */
    int completedWorkUnits();

    /**
     * @return the total number of work units.
     */
    int totalWorkUnits();

    /**
     * @return true if a job has suffered some sort of failure; otherwise false.
     */
    boolean isFailed();

}
