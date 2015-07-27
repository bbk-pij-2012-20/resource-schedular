package com.jpm.ipb;


import javafx.beans.binding.IntegerExpression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * SchedulingAlgorithm is responsible for selecting which Message should be processed next.
 * It stores the queue of Messages.
 * It is continuously updated by the ExpensiveResources on their status in terms of availability and if any are idle.
 */
public class SchedulingAlgorithm {

    private Gateway gateway;
    public static int NUMBER_OF_DIFFERENT_GROUPS = 4;
    private int numberOfResources;
    private Messages messages;
    private ResourceStatus resourceStatus;
    private ExecutorService executorService;

    /**
     * Constructor
     * @param gateway
     */
    public SchedulingAlgorithm(int numberOfResources, Gateway gateway, Messages messages) {

        this.gateway = gateway;
        this.messages = messages;
        this.numberOfResources = numberOfResources;
        resourceStatus = new ResourceStatus();
        createThreadPool();

    }


    /**
     * creates the thread pool according to the fixed total number of expensive resources
     */
    private void createThreadPool() {

        executorService = Executors.newFixedThreadPool(numberOfResources);

    }


    /**
     * called by Schedular Main method
     * generates a thread pool.
     * Continuously monitors state of ExpensiveResources, sends messages according to idle status of the resources
     * kills the threads if there's nothing left
     */
    public void schedule(LinkedList<Message> listOfMessages) {

        messageQueue = listOfMessages;

        while (!messageQueue.isEmpty()) {

            gateway.send(messageQueue.poll());
            readyQueue.add(getMessageOfCurrentGroup());

            if (thereAreNoAvailableResources) {

                System.out.println("no available resources");

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException ie) {

                    System.out.println("interrupted exception");

                }

            } else {

                System.out.println("idle resource found");
                Callable<String> schedule = new Schedule<String>();
                executorService.submit(schedule);

            }

        }

        shutdownThreadPool();
        terminateAllThreads();
        System.out.println("This is the end, my beautiful friend");

        class Schedule implements Callable<String> {

            public Schedule(){}

            @Override
            public String call() throws Exception {

                send();

            }

        }

    }

    private void sendAppropriateMessage() {

        if (thereIsAnIdleResource) {

            send();

        }

    }

    private Message getNextMessage() {

        return messageQueue.remove();

    }

    /**
     * Selects next Message according to type of last Message processed (by any of the resources).
     *
     * @return returns     Message that is the same group as the last Message to be processed.
     */
    private Message getMessageOfCurrentGroup() {


        try {

            if (messageQueue.isEmpty()) {

                throw new IllegalArgumentException("message list is empty");

            }

            for (Message msg : messageQueue) {

                if (msg.getGroupId() == groupIdOfLastMessageSent) {

                    message = messageQueue.remove(messageQueue.indexOf(msg));
                    break;

                }

            }

        } catch (IllegalArgumentException iae) {

            System.out.println(iae.getMessage());

        }

        groupIdOfLastMessageSent = message.getGroupId();
        return message;

    }


    public void shutdownThreadPool() {

        executorService.shutdown();

    }


    public void terminateAllThreads() {

        try {

            executorService.awaitTermination(2L, TimeUnit.SECONDS);

        } catch (InterruptedException ie) {

            System.out.println(ie.getMessage());

        }

    }

}