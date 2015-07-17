package com.jpm.ipb;

/**
 *
 */
public interface Message {

	/**
	 *
	 */
	void completed();

	/**
	 *
	 * @return
	 */
	boolean isCompleted();

	/**
	 *
	 * @return
	 */
	public abstract String getMessageNumber();

	/**
	 *
	 * @return
	 */
	public abstract int getGroupId();

}
