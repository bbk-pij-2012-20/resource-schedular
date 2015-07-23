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
	 *
	 */
	public void shutdownThreadPool();


	public void terminateAllThreads();

}
