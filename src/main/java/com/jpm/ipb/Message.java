package com.jpm.ipb;

/**
 *
 */
public abstract class Message {

	private String messageNumber;
	private boolean isCompleted;

	/**
	 * constructor
	 * @param messageNumber
	 */
	public Message(String messageNumber) {

		this.messageNumber = messageNumber;
		isCompleted = false;

	}

	/**
	 * default constructor
	 */
	public Message() {}

	/**
	 *
	 */
	public void completed() {

		isCompleted = true;

	}

	/**
	 *
	 * @return
	 */
	public String getCompletionStatus() {

		return messageNumber + "is completed processing";

	}

	/**
	 *
	 * @return
	 */
	public String getMessageNumber() {

		return messageNumber;

	}

	/**
	 *
	 * @return
	 */
	public abstract int getGroupId();

}
