package com.jpm.ipb;

/**
 * Manages the thread pool and asynchronous task execution
 */
public interface Gateway {

	/**
	 *
	 * @param message
	 */
	void send(Message message);

	/**
	 * shuts down the thread pool, only when the all the messages have been processed.
	 */
	public void shutdownThreadPool();

	/**
	 * terminates the threads.
	 */
	public void terminateAllThreads();

}
