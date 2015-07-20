package com.jpm.ipb;

import java.util.LinkedList;

public class ResourceSchedulerMain {

    public static void main(String[] args) {

        LinkedList<Message> listOfMessages = new LinkedList<>();
        listOfMessages.add(new MessageGroup1("msg1"));
        listOfMessages.add(new MessageGroup2("msg2"));
        listOfMessages.add(new MessageGroup2("msg3"));
        listOfMessages.add(new MessageGroup2("msg4"));
        listOfMessages.add(new MessageGroup1("msg5"));
        listOfMessages.add(new MessageGroup1("msg6"));
        listOfMessages.add(new MessageGroup2("msg7"));
        listOfMessages.add(new MessageGroup1("msg8"));
        listOfMessages.add(new MessageGroup2("msg9"));
        listOfMessages.add(new MessageGroup1("msg10"));
        listOfMessages.add(new MessageGroup2("msg11"));
        listOfMessages.add(new MessageGroup1("msg12"));
        listOfMessages.add(new MessageGroup1("msg13"));

        Scheduler scheduler = new Scheduler(listOfMessages);

        while (scheduler.hasMessagesToProcess()) {

            scheduler.schedule();

        }

    }

}
