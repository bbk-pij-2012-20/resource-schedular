package com.jpm.ipb;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the expensive resource.
 */
public class ExpensiveResource {

    public final int MAX_SIZE_OF_MESSAGE_QUEUE = 3;
    private AtomicInteger numberOfResourcesInUse;// use AtomicInteger, definitely not volatile
    private LinkedBlockingQueue<Message> resourcesMessageQueue;
    private long lastTimePoint;
    private ResourceStatus resourceStatus;
    private int numberOfResources;

     /**
     * Constructor
     */
    public ExpensiveResource(int numberOfResources) {

        this.numberOfResources = numberOfResources;
        resourcesMessageQueue = new LinkedBlockingQueue<>();
        numberOfResourcesInUse = new AtomicInteger(0);
        lastTimePoint = 0;
        resourceStatus = new ResourceStatus();
        updateResourceAvailability();
        updateResourceIdleStatus();

    }

    /**
     *
     * @param message
     */
    public void add(Message message) {

        resourcesMessageQueue.add(message); // adds the message to the END of the list, i.e. back of the queue.
        updateResourceAvailability();

    }

    /**
     * updates SchedulingAlgorithm on idle status of the resources
     */
    private void updateResourceIdleStatus() {


        System.out.println("updateresourceidle method");
        resourceStatus.thereIsAnIdleResource(numberOfResourcesInUse.get() != numberOfResources);

    }

    /**
     * updates SchedulingAlgorithm on availability of the resources
     */
    private void updateResourceAvailability() {
        System.out.println("updateresourceAVAILIBILITY method");
        System.out.println("true or false: "+(resourcesMessageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE));
        resourceStatus.thereAreNoAvailableResources(resourcesMessageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE);

    }

    /**
     *
     * @return
     */
    private String process() {

        long latestIncrease, currentTime = 0;
        int incrementedNumberOfResourcesBeingUsed = numberOfResourcesInUse.getAndIncrement();
        numberOfResourcesInUse.compareAndSet(numberOfResourcesInUse.get(), incrementedNumberOfResourcesBeingUsed);
        updateResourceAvailability();
        int decrementedNumberOfResourcesBeingUsed = numberOfResourcesInUse.getAndDecrement();
        numberOfResourcesInUse.compareAndSet(numberOfResourcesInUse.get(), decrementedNumberOfResourcesBeingUsed);
        updateResourceIdleStatus();
        Message messageBeingProcessed = resourcesMessageQueue.poll();

        for (int i = 1; i <= 1; i++) {

            currentTime = System.nanoTime();
            latestIncrease = currentTime - lastTimePoint;
            System.out.println("Group#" + messageBeingProcessed.getGroupId() + "   Message" + messageBeingProcessed.getMessageNumber() + "  ...processing " + i + ". Time taken: " + latestIncrease + " ns");
            lastTimePoint = currentTime;
            messageBeingProcessed.completed();

        }

        return messageBeingProcessed.getCompletionStatus();

    }

}
