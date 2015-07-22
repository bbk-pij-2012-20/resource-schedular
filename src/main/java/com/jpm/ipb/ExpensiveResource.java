package com.jpm.ipb;

import java.util.concurrent.Callable;

/**
 *
 */
public class ExpensiveResource implements Callable<String> { // callable (as opposed to runnable) is not needed here but I'm using it to learn about it

    private volatile boolean someResourceIsIdle;// Does using volatile here a good use of volatile ???

    /**
     * Constructor
     * @param   msg     message to be processed
     */
    public ExpensiveResource() {}

    @Override
    public String call() throws Exception {

       // Message[] msgList = new Message[2];//each resource is deemed 'unavailable' (by GatewayImpl) when its own list of messages, which is only 2 in size, is full
        process(msg);
        msg.completed();
        someResourceIsIdle = someResourceIsIdle && true;
        return "callable task reached end!";

    }

    /**
     *
     * @return          returns true if this expensive resource is idle
     */
    public boolean isAnyResourceIdle() {

        return someResourceIsIdle;

    }

    /**
     *
     * @param   msg     the Message to be processed by this resource
     */
    public void process(Message msg) {

        for (int i = 0; i < 10; i++) {

            System.out.println("Group: " + msg.getGroupId() + "msg#" + msg.getMessageNumber() + "...processing " + i);

            try {

                Thread.sleep(500);

            } catch (InterruptedException ie) {

                ie.printStackTrace();

            }

        }

    }

}
