package com.jpm.ipb;

import com.sun.org.apache.bcel.internal.generic.CHECKCAST;

import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 *
 */
public class ExpensiveResource implements Callable<String> { // callable (as opposed to runnable) is not needed here but I'm using it to learn about it

    public static final int TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();
    public final int MAX_SIZE_OF_MESSAGE_QUEUE = 3;
    private int resourcesBeingUsed = 0;// Does using volatile here a good use of volatile ???
    private LinkedList<Message> messageQueue;// should I use volatile here??

     /**
     * Constructor
     */
    public ExpensiveResource() {

        messageQueue = new LinkedList<>();

    }

    /**
     *
     * @param message
     */
    public void set(Message message) {

        messageQueue.add(message); // adds the message to the END of the list, i.e. back of the queue.
        updateResourceAvailability();

    }

    @Override
    public String call() throws Exception {

        resourcesBeingUsed++;

        if (resourcesBeingUsed == TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES) {

            SchedulerAlgorithm.StatusOfResources.thereIsAnIdleResource(false);

        } else {

            SchedulerAlgorithm.StatusOfResources.thereIsAnIdleResource(true);

        }

        process(messageQueue.getFirst());
        updateResourceAvailability();
        resourcesBeingUsed--;
        return "callable task reached end!";

    }

    /**
     *
     */
    private void updateResourceAvailability() {

        SchedulerAlgorithm.StatusOfResources.thereAreNoAvailableResources(messageQueue.size() == MAX_SIZE_OF_MESSAGE_QUEUE);

    }

    /**
     *
     * @param   message     the Message to be processed by this resource
     */
    private void process(Message message) {

        for (int i = 0; i < 10; i++) {

            System.out.println("Group: " + message.getGroupId() + "msg#" + message.getMessageNumber() + "...processing " + i);

            try {

                Thread.sleep(500); // this sleep line is just for aesthetics of the printout to console

            } catch (InterruptedException ie) {

                ie.printStackTrace();

            }

        }

        message.completed();

    }

}
