package com.jpm.ipb;

public class MessageWithGroup extends Message {

    private final int GROUP_ID;

    public MessageWithGroup(String messageNumber, int groupId) {

        super(messageNumber);
        GROUP_ID = groupId;

    }

    @Override
    public int getGroupId() {

        return GROUP_ID;

    }

}
