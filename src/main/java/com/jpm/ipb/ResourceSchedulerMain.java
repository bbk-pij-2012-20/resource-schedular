package com.jpm.ipb;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * main method, launcher
 */
public class ResourceSchedulerMain {

    public int TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES;

    public static void main(String[] args) {

        ResourceSchedulerMain main = new ResourceSchedulerMain();
        main.launch();

    }

    public ResourceSchedulerMain() {}

    public void launch() {

        Messages messages = new Messages();
        ExpensiveResource expensiveResource = new ExpensiveResource();
        Gateway gateway = new GatewayImpl(expensiveResource);
        SchedulingAlgorithm schedulingAlgorithm = new SchedulingAlgorithm(Runtime.getRuntime().availableProcessors(), gateway, messages);
        schedulingAlgorithm.schedule(listOfMessages);

    }

}
