package com.smarr.android.healthylifestyle.utilities.exception;


/**
 * NetworkException is a class for capturing the network exceptions which are occur at runtime
 *
 */
public class NetworkException extends BaseException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8018088533787724574L;

	public NetworkException(String detailMessage) {
		super("Networking/Server error: " + detailMessage);
	}

}