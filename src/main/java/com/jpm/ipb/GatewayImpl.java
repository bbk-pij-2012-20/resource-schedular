package com.jpm.ipb;

import java.util.concurrent.*;

/**
 * Responsible for creating the threads and executing the tasks
 */
public class GatewayImpl implements Gateway {

    private ExpensiveResource expensiveResource;
    private ExecutorService executorService;

    public GatewayImpl(ExpensiveResource expensiveResource) {

        this.expensiveResource = expensiveResource;

    }

    /*
    * NOTE: run() is sufficient. I only used call() (and therefore Future to practice playing with new stuff.
    */
    @Override
    public void send(Message message) {

        expensiveResource.addToResourcesQueue(message);
        expensiveResource.processNextMessage();

    }

}
