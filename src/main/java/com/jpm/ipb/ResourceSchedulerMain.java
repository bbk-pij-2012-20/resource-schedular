package com.jpm.ipb;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * main method, launcher
 */
public class ResourceSchedulerMain {

    public static void main(String[] args) {

        ResourceSchedulerMain main = new ResourceSchedulerMain();
        main.launch(main.makeAListOfMessages());

    }

    /**
     * makes a list of messages
     * @return
     */
    public Queue<Message> makeAListOfMessages() {

        Queue<Message> listOfMessages = new LinkedBlockingQueue<>();
        listOfMessages.add(new MessageGroup2("#1"));
        listOfMessages.add(new MessageGroup1("#2"));
        listOfMessages.add(new MessageGroup2("#3"));
        listOfMessages.add(new MessageGroup2("#4"));
        listOfMessages.add(new MessageGroup1("#5"));
        listOfMessages.add(new MessageGroup1("#6"));
        listOfMessages.add(new MessageGroup2("#7"));
        listOfMessages.add(new MessageGroup1("#8"));
        listOfMessages.add(new MessageGroup2("#9"));
        listOfMessages.add(new MessageGroup1("#10"));
        listOfMessages.add(new MessageGroup2("#11"));
        listOfMessages.add(new MessageGroup1("#12"));
        listOfMessages.add(new MessageGroup1("#13"));
        return listOfMessages;

    }

    /**
     * launches the program
     * @param listOfMessages
     */
    public void launch(Queue<Message> listOfMessages) {

        ExpensiveResource expensiveResource = new ExpensiveResource();
        Gateway gateway = new GatewayImpl(expensiveResource);
        SchedulingAlgorithm schedulingAlgorithm = new SchedulingAlgorithm(gateway);
        schedulingAlgorithm.schedule(listOfMessages);

    }

}
