package com.jpm.ipb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shahin.zibaee on 16/07/2015.
 */
public class ResourceSchedulerMain {

    private Scheduler scheduler;

    public static void main(String[] args) {

        List<Message> listOfMessages = new LinkedList<>();
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

        scheduler = new Scheduler(listOfMessages);

        while (scheduler.noMessagesToProcess()) {

            scheduler.schedule();

        }

    }

}
