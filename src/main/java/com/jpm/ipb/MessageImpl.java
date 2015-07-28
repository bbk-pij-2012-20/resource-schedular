package com.jpm.ipb;

public class MessageImpl extends Message {

    private final int GROUP_ID;

    public MessageImpl(String messageNumber, int groupId) {

        super(messageNumber);
        GROUP_ID = groupId;

    }

    @Override
    public int getGroupId() {

        return GROUP_ID;

    }

}
