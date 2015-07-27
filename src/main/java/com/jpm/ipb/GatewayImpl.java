package com.jpm.ipb;

import java.util.concurrent.*;

/**
 * Responsible for creating the threads and executing the tasks
 */
public class GatewayImpl implements Gateway, Callable<String> {

    private ExpensiveResource expensiveResource;
    private ExecutorService executorService;

    public GatewayImpl(ExpensiveResource expensiveResource) {

        this.expensiveResource = expensiveResource;

    }

    public String call() throws Exception {



        return "";

    }
    /*
    * NOTE: run() is sufficient. I only used call() (and therefore Future to practice playing with new stuff.
    */
    @Override
    public void send(Message message) {

        int i = 0;
        expensiveResource.set(message);
        String returnedFromCallable = "";
        Future<String> futRes = executorService.submit(expensiveResource);

        try {

            returnedFromCallable += "\n" + futRes.get();

        } catch (ExecutionException | InterruptedException ex) {

            System.out.println("exception");

        }

        System.out.println(returnedFromCallable);

    }

}
