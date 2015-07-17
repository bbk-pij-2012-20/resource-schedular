package com.jpm.ipb;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by shahin.zibaee on 16/07/2015.
 */
public class Scheduler {

    private Gateway gateway;
    private LinkedList<Message> messageQueue;
    private List<Message> readyQueue;
    private ExpensiveResource[] expensiveResources;
    private ExecutorService executorService;
    private final int NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    private int groupIdOfLastMessageSent;

    public Scheduler(LinkedList<Message> listOfMessages) {

        gateway = new GatewayImpl();
        messageQueue = listOfMessages;
        groupIdOfLastMessageSent = messageQueue.getFirst().getGroupId();
        readyQueue = new LinkedList<>();
        expensiveResources = new ExpensiveResource[NUMBER_OF_EXPENSIVE_RESOURCES];
        createThreadPool();

    }

    /**
     *
     * @return
     */
    public boolean noMessagesToProcess() {

        return messageQueue.isEmpty();

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
     * @return
     */
    private Message popSameGroupAsLastMessage() {

        Message result = null;
        int indexOfMessageToBeRemoved = 0;

        for (Message msg : messageQueue) {

            if (msg.getGroupId() == (groupIdOfLastMessageSent)) {

                indexOfMessageToBeRemoved = messageQueue.indexOf(msg);
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

            if (expensiveResources[i].isIdle()) {

                idleExpensiveResourceFound = true;

            }

            i++;

        }

        return idleExpensiveResourceFound;

    }

}
