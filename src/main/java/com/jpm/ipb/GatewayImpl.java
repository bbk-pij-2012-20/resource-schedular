package com.jpm.ipb;

import java.util.concurrent.*;

/**
 * Responsible for creating the threads and executing the tasks
 */
public class GatewayImpl implements Gateway {

    private ExpensiveResource expensiveResource;
    private ExecutorService executorService;

    public GatewayImpl(ExpensiveResource expensiveResource) {

        this.expensiveResource = expensiveResource;
        createThreadPool();

    }

    /**
     *  creates the thread pool according to the fixed total number of expensive resources
     */
    private void createThreadPool() {

        executorService = Executors.newFixedThreadPool(ExpensiveResource.TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /*
    * PLEASE NOTE: my use of Callable (and therefore use of Future etc) is entirely a training exercise choice,
    * it's completely OTT and unnecessary for the program design itself!!
    */
    @Override
    public void send(Message message) {

        int i = 0;
        expensiveResource.set(message);
        String returnedFromCallable = "";

        try {

            while (i < ExpensiveResource.TOTAL_NUMBER_OF_EXPENSIVE_RESOURCES) {

                Future<String> futRes = executorService.submit(expensiveResource);
                i++;
                returnedFromCallable += "\n" + futRes.get(2L, TimeUnit.SECONDS);
                // get(Long,TimeUnit)"Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available."

            }

        } catch (ExecutionException | InterruptedException | TimeoutException ex) {

            System.out.println(ex.getMessage());

        }

        System.out.println(returnedFromCallable);

    }

    @Override
    public void shutdownThreadPool() {

        executorService.shutdown();

    }

    @Override
    public void terminateAllThreads() {

        try {

            executorService.awaitTermination(2L, TimeUnit.SECONDS);

        } catch (InterruptedException ie) {

            System.out.println(ie.getMessage());

        }

    }

}
