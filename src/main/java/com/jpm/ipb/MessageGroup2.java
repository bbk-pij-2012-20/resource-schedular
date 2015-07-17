package com.jpm.ipb;

/**
 * Created by shahin.zibaee on 17/07/2015.
 */
public class MessageGroup2 implements Message {

    private final static int GROUP_ID = 2;
    private boolean isCompleted;
    private String messageNumber;

    public MessageGroup2(String messageNumber) {

        this.messageNumber = messageNumber;
        isCompleted = false;

    }

    @Override
    public void completed() {

        isCompleted = true;

    }

    @Override
    public boolean isCompleted() {

        return isCompleted;

    }

    @Override
    public String getMessageNumber() {

        return messageNumber;

    }

    @Override
    public int getGroupId() {

        return GROUP_ID;

    }

}