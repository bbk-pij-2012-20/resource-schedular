package com.jpm.ipb;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Scheduler {

    private Gateway gateway;
    private LinkedList<Message> messageQueue;
    private ExpensiveResource[] expensiveResources;
    private ExecutorService executorService;
    private final int NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    protected int groupIdOfLastMessageSent;

    public Scheduler(LinkedList<Message> listOfMessages) {

        gateway = new GatewayImpl();
        messageQueue = listOfMessages;
        groupIdOfLastMessageSent = messageQueue.getFirst().getGroupId();
        expensiveResources = new ExpensiveResource[NUMBER_OF_EXPENSIVE_RESOURCES];
        createThreadPool();
        initExpensiveResources();

    }

    /**
     *
     * @return returns true if the list is not empty
     */
    public boolean hasMessagesToProcess() {

        return !messageQueue.isEmpty();

    }

    /**
     *
     */
    private void createThreadPool() {

        executorService = Executors.newFixedThreadPool(NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /**
     * Gives the first message in the queue to each resource.
     */
    private void initExpensiveResources() {

        int i = 0;

        while (i < NUMBER_OF_EXPENSIVE_RESOURCES) {

            Future<Boolean> futRes = executorService.submit(new ExpensiveResource(messageQueue.getFirst()));
            i++;

        }

    }

    /**
     *
     */
    public void schedule() {

        groupIdOfLastMessageSent = messageQueue.peek().getGroupId();

        if (idleResourceFound()) {

            gateway.send(messageQueue.pop());

        } else {

            //this is wrong - the spec says if no resources are available, msgs should not be sent to the gateway.,
            gateway.send(popSameGroupAsLastMessage());

        }

        if (messageQueue.isEmpty()) {

            executorService.shutdown();

        }

    }

    /**
     * @return  returns the Message that is the same group as the last Message to be processed.
     */
    private Message popSameGroupAsLastMessage() {

        Message result = null;

        for (Message msg : messageQueue) {

            if (msg.getGroupId() == (groupIdOfLastMessageSent)) {

                result = messageQueue.remove(messageQueue.indexOf(msg));
                break;

            }

        }

        return result;

    }

    /**
     * @return returns true if one of the expensive resources is currently idle
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

}
