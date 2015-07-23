package com.jpm.ipb;

public class MessageGroup2 extends Message {

    private final int GROUP_ID = 2;

    public MessageGroup2(String messageNumber) {

        super(messageNumber);

    }

    @Override
    public int getGroupId() {

        return GROUP_ID;

    }

}
