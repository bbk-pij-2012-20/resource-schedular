package com.jpm.ipb;
//testing a new commit
/**
 * Created by shahin.zibaee on 16/07/2015.
 */
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
     * @return
     *e
    public boolean isIdle() {

        return idle;

    }


    /**
     *
     * @param msg
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
