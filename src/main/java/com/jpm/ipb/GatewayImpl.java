package com.jpm.ipb;

import java.util.concurrent.*;

/**
 *
 */
public class GatewayImpl implements Gateway {

    private ExpensiveResource expensiveResource;
    private ExecutorService executorService;
    public static final int NUMBER_OF_EXPENSIVE_RESOURCES = Runtime.getRuntime().availableProcessors();

    public GatewayImpl(ExpensiveResource expensiveResource) {

        this.expensiveResource = expensiveResource;
        createThreadPool();
        initExpensiveResources();

    }

    /**
     *
     */
    private void createThreadPool() {

        executorService = Executors.newFixedThreadPool(NUMBER_OF_EXPENSIVE_RESOURCES);

    }

    /**
     * Initialises the ExpensiveResources with the first message in the queue to each resource.
     * Creates a specified number of worker threads (same number as number of cpu in this pc) and submits each ExpensiveResource to thread.
     * <p>
     * PLEASE NOTE: my use of Callable (and therefore use of Future etc) is entirely a training exercise choice,
     * it's completely OTT and unnecessary for the program design itself!!
     */
    private void initExpensiveResources() {

        String returnedFromCallable = "";

        for (int i = 0; i < NUMBER_OF_EXPENSIVE_RESOURCES; i++) {

            expensiveResources[i] = new ExpensiveResource(messageQueue.getFirst());

        }

        int i = 0;

        try {

            while (i < NUMBER_OF_EXPENSIVE_RESOURCES) {

                Future<String> futRes = executorService.submit(expensiveResources[i]);
                i++;
                returnedFromCallable += "\n" + futRes.get(2L, TimeUnit.SECONDS);
                // get(Long,TimeUnit)"Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available."

            }

        } catch (ExecutionException | InterruptedException | TimeoutException ex) {

            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void send(Message message) {



    }

}
