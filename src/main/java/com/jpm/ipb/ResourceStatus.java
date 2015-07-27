package com.jpm.ipb;

/**
 * Created by shahin.zibaee on 27/07/2015.
 */
public class ResourceStatus {

    private boolean thereIsAnIdleResource;
    private boolean thereAreNoAvailableResources;

    public ResourceStatus() {}

    /**
     * only called by ExpensiveResources
     */
    public synchronized void thereIsAnIdleResource(boolean trueFalse) {

        thereIsAnIdleResource = trueFalse;

    }

    /**
     * only called by ExpensiveResources
     */
    public synchronized void thereAreNoAvailableResources(boolean trueFalse) {

        thereAreNoAvailableResources = trueFalse;

    }

}
