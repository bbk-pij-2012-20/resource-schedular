package com.jpm.ipb;

public class MessageGroup1 extends Message {

    private final int GROUP_ID = 1;

    public MessageGroup1(String messageNumber) {

        super(messageNumber);

    }

    @Override
    public int getGroupId() {

        return GROUP_ID;

    }

}
