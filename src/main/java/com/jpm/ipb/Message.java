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
	 * sets the completed flag to true
	 */
	public void completed() {

		isCompleted = true;

	}

	/**
	 * provides information on whether a message has been processed or not yet.
	 * @return
	 */
	public String getCompletionStatus() {

		return "message " + messageNumber + (isCompleted? " has completed processing\n": "not completed yet\n");

	}

	/**
	 *
	 * @return	the message number
	 */
	public String getMessageNumber() {

		return messageNumber;

	}

	/**
	 *
	 * @return	the group id number
	 */
	public abstract int getGroupId();

}
