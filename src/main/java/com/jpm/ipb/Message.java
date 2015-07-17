package com.jpm.ipb;

public abstract class Message {

	/**
	 *
	 */
	public void completed() {// When this method is called, message releases a thread



	}

	public void run() {



	}

	/**
	 *
	 * @return
	 */
	public abstract int getGroupId();

	/**
	 *
	 * @return
	 */
	public abstract int getId();
	
}
