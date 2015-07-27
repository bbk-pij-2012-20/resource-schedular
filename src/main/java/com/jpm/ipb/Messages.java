package com.jpm.ipb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shahin.zibaee on 27/07/2015.
 */
public class Messages {

    private int groupIdOfNextMessageToProcess;
    private Map<Integer, List<Message>> messagesByGroup;


    public Messages() {

        messagesByGroup = new HashMap<>();
        sortByGroups(createListOfMessages());

    }
    /**
     * makes a list of messages
     * @return
     */
    private List<Message> createListOfMessages() {

        List<Message> listOfMessages = new LinkedList<>();
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



    public int getGroupIdOfNextMessageToProcess() {

        return 1;

    }

}
