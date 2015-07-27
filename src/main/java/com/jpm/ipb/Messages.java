package com.jpm.ipb;

import java.util.*;

/**
 * Created by shahin.zibaee on 27/07/2015.
 */
public class Messages {

    private int groupIdOfNextMessageToProcess;
    private Map<Integer, List<Message>> messagesByGroup;
    private List<Message> messages;

    public Messages() {

        messagesByGroup = new HashMap<>();
        sortByGroups(createListOfMessages());
        groupIdOfNextMessageToProcess = 0;

    }
    /**
     * makes a list of messages
     * @return
     */
    private List<Message> createListOfMessages() {

        List<Message> listOfMessages = new LinkedList<>();
        listOfMessages.add(new MessageWithGroup("#1", 1));
        listOfMessages.add(new MessageWithGroup("#2", 2));
        listOfMessages.add(new MessageWithGroup("#3", 2));
        listOfMessages.add(new MessageWithGroup("#4", 2));
        listOfMessages.add(new MessageWithGroup("#5", 3));
        listOfMessages.add(new MessageWithGroup("#6", 3));
        listOfMessages.add(new MessageWithGroup("#7", 2));
        listOfMessages.add(new MessageWithGroup("#8", 1));
        listOfMessages.add(new MessageWithGroup("#9", 1));
        listOfMessages.add(new MessageWithGroup("#10", 2));
        listOfMessages.add(new MessageWithGroup("#11", 3));
        listOfMessages.add(new MessageWithGroup("#12", 4));
        listOfMessages.add(new MessageWithGroup("#13", 4));
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


    private void setGroupIdOfNextMessageToProcess() {

        //TODO

    }

    public int getGroupIdOfNextMessageToProcess() {

        //TODO
        return 1;

    }

}
