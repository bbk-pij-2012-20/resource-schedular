package com.jpm.ipb;
//testing a new commit

public class ExpensiveResource implements Runnable {

    private boolean idle;
    private Message msg;

    /**
     *
     */
    public ExpensiveResource(Message msg) {

        idle = true;
        this.msg = msg;

    }

    /**
     *
     */
    public void run() {

        idle = false;
        process(msg);
        msg.completed();
        idle = true;

    }

    /**
     *
     * @return true if this expensive resource is idle
     */
    public boolean isIdle() {

        return idle;

    }


    /**
     *
     * @param msg  the Message to be processed by this resource
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
