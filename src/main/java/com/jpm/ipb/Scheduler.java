package com.jpm.ipb;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Scheduler <T> {

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
     * Creates a specified number of worker threads (same number as number of cpu in this pc)
     * Using Callable, it also returns a value upon execution of the thread activity.
     * PLEASE NOTE: my use of Callable (and therefore use of Future etc) is entirely a training exercise choice,
     * it's completely OTT and unnecessary for the program design itself!
     */
    private void initExpensiveResources() {

        int i = 0;
        String returnedFromCallable = "";

        try {

            while (i < NUMBER_OF_EXPENSIVE_RESOURCES) {

                Future<String> futRes = executorService.submit(new ExpensiveResource(messageQueue.getFirst()));
                i++;
                returnedFromCallable += "\n" + futRes.get(2L, TimeUnit.SECONDS);
                // get(Long,TimeUnit)"Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available."

            }

        } catch (ExecutionException | InterruptedException | TimeoutException ex) {

            System.out.println(ex.getMessage());

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
