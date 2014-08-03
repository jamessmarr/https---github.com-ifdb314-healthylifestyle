package com.smarr.android.healthylifestyle.utilities.exception;


public class UnauthorizedException extends BaseException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8346488945127992008L;

	public UnauthorizedException(String detailMessage) {
		super("Unauthorized error: " + detailMessage);
	}

}
