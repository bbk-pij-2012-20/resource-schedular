package com.jpm.ipb;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by shahin.zebaee on 16/07/2015.
 */
public class Scheduler {

    private Gateway gateway;
    private List<Message> messageQueue;
    private List<Message> readyQueue;
    private ExpensiveResource[] expensiveResources;
    private ExecutorService executorService;
    private final int NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    private String lastMessageSent;

    public Scheduler() {

        lastMessagePopped = messageQueue.getFirst();
        gateway = new GatewayImpl();
        messageQueue = new LinkedList<>();
        readyQueue = new LinkedList<>();
        expensiveResources = new ExpensiveResource[NUMBER_OF_EXPENSIVE_RESOURCES];
        createThreadPool();

    }

    public boolean noMessagesToProcess() {

        return messageQueue.empty();

    }

    /**
     *
     */
    private void createThreadPool() {

        executorService = Executors.newFixedThreadPool(NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /**
     *
     */
    private void initExpensiveResources() {

        int i = 0;

        while (i < NUMBER_OF_EXPENSIVE_RESOURCES) {

            executorService.execute(new ExpensiveResource());
            i++;

        }

    }

    /**
     *
     */
    public void schedule() {

        lastMessageSent = messageQueue.peek().getGroupId();

        if (idleResourceFound()) {

            gateway.sendMessage(messageQueue.pop());

        } else {

            //this is wrong - the spec says if no resources are available, msgs should not be sent to the gateway.,
            gateway.sendMessage(messageQueue.popSameGroupAsLastMessage());

        }

        if (messageQueue.empty()) {

            executorService.shutdown();

        }

    }

    /**
     * @return
     */
    private Message popSameGroupAsLastMessage() {

        Message result = null;
        int indexOfMessageToBeRemoved = 0;

        for (Message msg : messageQueue) {

            if (msg.getGroupId().equals(lastMessageSent)) {

                indexOfMessageToBeRemoved = messageQueue.getIndexOf(msg);
                result = messageQueue.remove(indexOfMessageToBeRemoved);
                break;

            }

        }

        return result;

    }

    /**
     * @return
     */
    private boolean idleResourceFound() {

        boolean idleExpensiveResourceFound = false;
        int i = 0;

        while (i < expensiveResources.length && !idleExpensiveResourceFound) {

            if (expensiveResources[i].idle) {

                idleExpensiveResourceFound = true;

            }

            i++;

        }

        return idleExpensiveResourceFound;

    }

}
