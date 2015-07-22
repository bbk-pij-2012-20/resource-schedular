package com.jpm.ipb;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Scheduler holds all the logic and 'algorithms'.
 * It stores the queue of Messages.
 * It continuously monitors the state of the ExpensiveResources to find which, if any, are 'idle' and 'available'.
 * According to the state of the resources, the next Message to be sent to the resources is selected and sent, via Gateway.
 */
public class Scheduler {

    private Gateway gateway;
    private LinkedList<Message> messageQueue;
    protected int groupIdOfLastMessageSent;

    /**
     * @param listOfMessages Messages
     */
    public Scheduler(ExpensiveResource expensiveResource, Gateway gateway) {

        sendFirstMessages();

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

            for (int i = 0; i < GatewayImpl.NUMBER_OF_EXPENSIVE_RESOURCES; i++) {

                if (idleResourceFound()) {

                    sendAnyMessage();

            } else {

                sendMessageOfSameGroupAsLastMessage();

            }

            if (messageQueue.isEmpty()) {

                executorService.shutdown();
                break;

            }

        }

        try {

            executorService.awaitTermination(2L, TimeUnit.SECONDS);

        } catch (InterruptedException ie) {

            System.out.println(ie.getMessage());

        }

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
     * Determines whether any resource is idle. (The idle property is not specific to each resource)
     *
     * @return returns     true if one of the expensive resources is currently idle
     */
    private boolean idleResourceFound() {

        int i = 0;
        boolean idleResourceFound = false;

        while (i < expensiveResources.length && !idleResourceFound) {

            if (expensiveResources[i].isIdle()) {

                idleResourceFound = true;

            }

            i++;

        }

        return idleResourceFound;

    }

    private void sendFirstMessages() {

        for (int i = 0; i < GatewayImpl.NUMBER_OF_EXPENSIVE_RESOURCES; i++) {

            sendAnyMessage();

        }

    }
}

}
