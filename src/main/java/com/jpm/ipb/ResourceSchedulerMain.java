package com.jpm.ipb;

/**
 * Created by shahin.zebaee on 16/07/2015.
 */
public class ResourceSchedulerMain {

    private Scheduler scheduler;


    public static void main(String[] args) {

        scheduler = new Scheduler();
        System.out.println("main");

        while (scheduler.noMessagesToProcess()) {

            scheduler.schedule();

        }

    }

}
