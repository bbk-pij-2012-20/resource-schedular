package com.jpm.ipb;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates a list of messages.
 * Sorts its messages into separate lists by their group ids. Sorting is done in a separate thread, so the data structure chosen is from java.util.concurrent
 * Keeps track of the current group id of messages to process (in order to reduce interleaving message processing).
 */
public class Messages {

    private int groupIdOfNextMessageToProcess;
    private Map<Integer, List<Message>> messagesByGroup;
    private List<Message> messages;

    public Messages() {

        messagesByGroup = new ConcurrentHashMap<>();//HashMap<>();
        sortByGroups(createListOfMessages());
        groupIdOfNextMessageToProcess = 0;

    }

    /**
     * makes a list of messages with unique message number and with a group number
     * @return
     */
    private List<Message> createListOfMessages() {

        List<Message> listOfMessages = new LinkedList<>();
        String msgNum = "";
        Random r = new Random();
        int grpNum = 0;

        for (int i = 0; i < 200; i++) {

            msgNum = "#" + i;
            grpNum = r.nextInt(4) + 1;
            listOfMessages.add(new MessageImpl(msgNum, grpNum));

        }

        return listOfMessages;

    }

    /**
     *  sorting messages into
     * @param messages
     */
    private void sortByGroups(List<Message> messages) {

        new Thread() {

            public void run() {

                for (Message message : messages) {

                    if (messagesByGroup.containsKey(message.getGroupId())) {

                        messagesByGroup.get(message.getGroupId()).add(message);

                    } else {

                        List<Message> newMessageList = new LinkedList<>();
                        newMessageList.add(message);
                        messagesByGroup.put(message.getGroupId(), newMessageList);

                    }

                }

            }

        }.start();

    }

    /**
     *
     * @return
     */
    public Message getNextMessageFromCurrentGroupId() {

        LinkedList<Message> messages = new LinkedList<>();
        Iterator it = messagesByGroup.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry keyValuePair = (Map.Entry) it.next(); //<Integer, LinkedList<Message>>

            if (groupIdOfNextMessageToProcess == (int) keyValuePair.getKey()) {

                messages =  (LinkedList<Message>) keyValuePair.getValue();
                it.remove();
                break;

            }

            it.remove(); // avoids a ConcurrentModificationException

        }

        return messages.poll();

    }

    /**
     *
     */
    private void setGroupIdOfNextMessageToProcess() {

        //TODO

    }

    /**
     *
     * @return
     */
    public int getGroupIdOfNextMessageToProcess() {

        //TODO
        return 1;

    }

}
