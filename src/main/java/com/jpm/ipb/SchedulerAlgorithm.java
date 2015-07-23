package com.jpm.ipb;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * SchedulerAlgorithm is responsible for selecting which Message should be processed next.
 * It stores the queue of Messages.
 * It is continuously updated by the ExpensiveResources on their status in terms of availability and if any are idle.
 */
public class SchedulerAlgorithm {

    private Gateway gateway;
    private LinkedList<Message> messageQueue;
    protected int groupIdOfLastMessageSent;
    private ExpensiveResource expensiveResource;
    private boolean thereIsAnIdleResource;
    private boolean thereAreNoAvailableResources;

    /**
     * Constructor
     * @param expensiveResource
     * @param gateway
     */
    public SchedulerAlgorithm(ExpensiveResource expensiveResource, Gateway gateway) {

        this.gateway = gateway;
        this.expensiveResource = expensiveResource;
        sendFirstMessages();
        thereIsAnIdleResource = true;
        thereAreNoAvailableResources = false;

    }

    /**
     * @return returns     true if the list is not empty
     */
    public boolean hasMessagesToProcess() {

        return !messageQueue.isEmpty();

    }

    /**
     * Continuously monitors state of ExpensiveResources, sends messages according to idle status of the resources
     * kills the threads if there's nothing left
     */
    public void schedule(LinkedList<Message> listOfMessages) {

        messageQueue = listOfMessages;

        while (true) {

            if (thereAreNoAvailableResources) {

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException ie) {

                    System.out.println("interrupted exception");

                }

            }

            if (thereIsAnIdleResource) {

                sendAnyMessage();

            } else {

                sendMessageOfSameGroupAsLastMessage();

            }

            if (messageQueue.isEmpty()) {

                gateway.shutdownThreadPool();
                break;

            }

        }

        gateway.terminateAllThreads();
        System.out.println("finished all threads");

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
        gateway.send(messageQueue.pop());

    }

    /**
     * Selects next Message according to type of last Message processed (by any of the resources).
     *
     * @return returns     Message that is the same group as the last Message to be processed.
     */
    private Message popSameGroupAsLastMessage() {

        Message message = null;

        for (Message msg : messageQueue) {

            if (msg.getGroupId() == (groupIdOfLastMessageSent)) {

                message = messageQueue.remove(messageQueue.indexOf(msg));
                break;

            }

        }

        groupIdOfLastMessageSent = message.getGroupId();
        return message;

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
     * nested class holding status of resources.
     */
    protected static class StatusOfResources {

        private static boolean thereAreNoAvailableResources;
        private static boolean thereIsAnIdleResource;

        public StatusOfResources() {

            thereIsAnIdleResource = !(thereAreNoAvailableResources = false);

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
