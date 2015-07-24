package com.jpm.ipb;

import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * SchedulingAlgorithm is responsible for selecting which Message should be processed next.
 * It stores the queue of Messages.
 * It is continuously updated by the ExpensiveResources on their status in terms of availability and if any are idle.
 */
public class SchedulingAlgorithm {

    private Gateway gateway;
    private LinkedList<Message> messageQueue;
    protected int groupIdOfLastMessageSent;
    private boolean thereIsAnIdleResource;
    private boolean thereAreNoAvailableResources;

    /**
     * Constructor
     *
     * @param gateway
     */
    public SchedulingAlgorithm(Gateway gateway) {

        this.gateway = gateway;
        thereIsAnIdleResource = true;
        thereAreNoAvailableResources = false;

    }

    /**
     * Continuously monitors state of ExpensiveResources, sends messages according to idle status of the resources
     * kills the threads if there's nothing left
     */
    public void schedule(LinkedList<Message> listOfMessages) {

        messageQueue = listOfMessages;
        sendFirstMessages();

        while (!messageQueue.isEmpty()) {

            if (thereAreNoAvailableResources) {

                System.out.println("no available resources");

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException ie) {

                    System.out.println("interrupted exception");

                }

            } else if (thereIsAnIdleResource) {

                System.out.println("idle resource found");
                sendAnyMessage();// this needs to be execute.. to launch a thread

            } else {

                System.out.println("no idle resources. Available resources.");
                sendMessageOfSameGroupAsLastMessage();

            }

        }

        gateway.shutdownThreadPool();
        gateway.terminateAllThreads();
        System.out.println("This is the end, my beautiful friend");

    }

    /**
     * sends the first messages to the resources
     */
    private void sendFirstMessages() {

        sendAnyMessage();

        for (int i = 1; i < ExpensiveResource.TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES; i++) {

            sendMessageOfSameGroupAsLastMessage();

        }

    }

    /**
     * Sends first Message found to belong to same group as most recently processed Message
     */
    private void sendMessageOfSameGroupAsLastMessage() {

        gateway.send(popSameGroupAsLastMessage());

    }

    /**
     * Sends Message from front of queue (regardless of the Message's group id).
     */
    private void sendAnyMessage() {

        groupIdOfLastMessageSent = messageQueue.peek().getGroupId();

        try {

            gateway.send(messageQueue.remove());// retrieves and removes the head of this queue

        } catch (NoSuchElementException nse) {

            nse.printStackTrace();

        }

    }

    /**
     * Selects next Message according to type of last Message processed (by any of the resources).
     *
     * @return returns     Message that is the same group as the last Message to be processed.
     */
    private Message popSameGroupAsLastMessage() {

        Message message = messageQueue.getFirst();

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

    /**
     * nested class holding status of resources.
     */
    protected static class StatusOfResources {

        private static boolean thereAreNoAvailableResources;
        private static boolean thereIsAnIdleResource;

        public StatusOfResources() {

            thereIsAnIdleResource(true);
            thereAreNoAvailableResources(false);

        }

        /**
         * only called by ExpensiveResources
         */
        public static synchronized void thereIsAnIdleResource(boolean trueFalse) {

            thereIsAnIdleResource = trueFalse;

        }

        /**
         * only called by ExpensiveResources
         */
        public static synchronized void thereAreNoAvailableResources(boolean trueFalse) {

            thereAreNoAvailableResources = trueFalse;

        }

    }

}
