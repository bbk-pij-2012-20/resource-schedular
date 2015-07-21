package com.jpm.ipb;

import java.util.concurrent.Callable;

/**
 *
 */
public class ExpensiveResource implements Callable<String> { // callable (as opposed to runnable) is not needed here but I'm using it to learn about it

    private boolean idle;
    private Message msg;

    /**
     * Constructor
     * @param   msg     message to be processed
     */
    public ExpensiveResource(Message msg) {

        idle = true;
        this.msg = msg;

    }

    @Override
    public String call() throws Exception {

        idle = false;
        process(msg);
        msg.completed();
        idle = true;
        return "callable task completed!";

    }

    /**
     *
     * @return          returns true if this expensive resource is idle
     */
    public boolean isIdle() {

        return idle;

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
