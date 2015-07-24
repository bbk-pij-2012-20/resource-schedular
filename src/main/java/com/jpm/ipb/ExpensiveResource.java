package com.jpm.ipb;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the expensive resource.
 */
public class ExpensiveResource implements Callable<String> { // callable (as opposed to runnable) is not needed here but I'm using it to learn about it

    public static final int TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    public final int MAX_SIZE_OF_MESSAGE_QUEUE = 3;
    private AtomicInteger numberOfResourcesInUse;// use AtomicInteger, definitely not volatile
    private LinkedBlockingQueue<Message> resourcesMessageQueue;
    private long lastTimePoint;

     /**
     * Constructor
     */
    public ExpensiveResource() {

        resourcesMessageQueue = new LinkedBlockingQueue<>();
        numberOfResourcesInUse = new AtomicInteger(0);
        lastTimePoint = 0;
        updateResourceAvailability();
        updateResourceIdleStatus();

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

        int incrementedNumberOfResourcesBeingUsed = numberOfResourcesInUse.getAndIncrement();
        numberOfResourcesInUse.compareAndSet(numberOfResourcesInUse.get(), incrementedNumberOfResourcesBeingUsed);
        String completionStatus = process(resourcesMessageQueue.poll());
        updateResourceAvailability();
        int decrementedNumberOfResourcesBeingUsed = numberOfResourcesInUse.getAndDecrement();
        numberOfResourcesInUse.compareAndSet(numberOfResourcesInUse.get(), decrementedNumberOfResourcesBeingUsed);
        updateResourceIdleStatus();
        return completionStatus;

    }

    /**
     * updates SchedulingAlgorithm on idle status of the resources
     */
    private void updateResourceIdleStatus() {

        System.out.println("updateresourceidle method");
        SchedulingAlgorithm.StatusOfResources.thereIsAnIdleResource(numberOfResourcesInUse.get() != TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /**
     * updates SchedulingAlgorithm on availability of the resources
     */
    private void updateResourceAvailability() {
        System.out.println("updateresourceAVAILIBILITY method");
        System.out.println("true or false: "+(resourcesMessageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE));
        SchedulingAlgorithm.StatusOfResources.thereAreNoAvailableResources(resourcesMessageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE);

    }

    /**
     *
     * @param   message     the Message to be processed by this resource
     */
    private String process(Message message) {

        long latestIncrease, currentTime = 0;

        for (int i = 1; i <= 1; i++) {

            currentTime = System.nanoTime();
            latestIncrease = currentTime - lastTimePoint;
            System.out.println("Group#" + message.getGroupId() + "   Message" + message.getMessageNumber() + "  ...processing " + i + ". Time taken: " + latestIncrease + " ns");
            lastTimePoint = currentTime;
            message.completed();

        }

        return message.getCompletionStatus();

    }

}
