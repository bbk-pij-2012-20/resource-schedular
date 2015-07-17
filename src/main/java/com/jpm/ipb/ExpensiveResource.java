package com.jpm.ipb;

/**
 * Created by shahin.zebaee on 16/07/2015.
 */
public class ExpensiveResource implements Runnable {

    private boolean idle;

    public ExpensiveResource(Message) {

        idle = true;

    }

    public void run() {

        idle = false;
        process(msg);
        msg.completed();
        idle = true;

    }

    public void process(Message msg) {

        for (int i = 0; i < 10; i++) {

            System.out.println("processing....." + i);
            System.sleep(500);

        }

    }

}
