package com.jpm.ipb;

/**
 *
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
