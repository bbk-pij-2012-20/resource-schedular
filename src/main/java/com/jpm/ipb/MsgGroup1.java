package com.jpm.ipb;

/**
 * Created by shahin.zebaee on 16/07/2015.
 */
public class MsgGroup1 extends Message {

    private final static String GROUP = 1;

    @Override
    public void completed() {

    }

    @Override
    public int getGroupId() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

}
