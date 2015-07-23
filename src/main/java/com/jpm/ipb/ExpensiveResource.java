package com.jpm.ipb;

import com.sun.org.apache.bcel.internal.generic.CHECKCAST;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the expensive resource.
 */
public class ExpensiveResource implements Callable<String> { // callable (as opposed to runnable) is not needed here but I'm using it to learn about it

    public static final int TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    public final int MAX_SIZE_OF_MESSAGE_QUEUE = 3;
    private AtomicInteger numberOfResourcesBeingUsed;// use AtomicInteger, definitely not volatile
    private LinkedBlockingQueue<Message> resourcesMessageQueue;//

     /**
     * Constructor
     */
    public ExpensiveResource() {

        resourcesMessageQueue = new LinkedBlockingQueue<>();
        numberOfResourcesBeingUsed = new AtomicInteger(0);

    }

    /**
     *
     * @param message
     */
    public void set(Message message) {

        resourcesMessageQueue.add(message); // adds the message to the END of the list, i.e. back of the queue.
        updateResourceAvailability();

    }

    @Override
    public String call() throws Exception {

        int incrementedNumberOfResourcesBeingUsed = numberOfResourcesBeingUsed.getAndIncrement();
        numberOfResourcesBeingUsed.compareAndSet(numberOfResourcesBeingUsed.get(), incrementedNumberOfResourcesBeingUsed);
        String completionStatus = process(resourcesMessageQueue.poll());
        updateResourceAvailability();
        int decrementedNumberOfResourcesBeingUsed = numberOfResourcesBeingUsed.getAndDecrement();
        numberOfResourcesBeingUsed.compareAndSet(numberOfResourcesBeingUsed.get(), decrementedNumberOfResourcesBeingUsed);
        updateResourceIdleStatus();
        return completionStatus;

    }

    /**
     * updates SchedulingAlgorithm on idle status of the resources
     */
    private void updateResourceIdleStatus() {

        SchedulingAlgorithm.StatusOfResources.thereIsAnIdleResource(numberOfResourcesBeingUsed.get() != TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /**
     * updates SchedulingAlgorithm on availability of the resources
     */
    private void updateResourceAvailability() {

        SchedulingAlgorithm.StatusOfResources.thereAreNoAvailableResources(resourcesMessageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE);

    }

    /**
     *
     * @param   message     the Message to be processed by this resource
     */
    private String process(Message message) {

        for (int i = 1; i <= 5; i++) {

            System.out.println("Group#" + message.getGroupId() + "   Message" + message.getMessageNumber() + "  ...processing " + i);

            try {

                Thread.sleep(500); // this sleep line is just for aesthetics of the printout to console

            } catch (InterruptedException ie) {

                ie.printStackTrace();

            }

        }

        return message.getCompletionStatus();

    }

}
