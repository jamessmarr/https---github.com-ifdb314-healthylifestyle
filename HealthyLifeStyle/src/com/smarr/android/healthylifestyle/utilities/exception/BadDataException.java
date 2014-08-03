package com.smarr.android.healthylifestyle.utilities.exception;


public class BadDataException extends BaseException {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2380648634711187971L;

	public BadDataException(String detailMessage) {
		super("Bad/Invalid Data: " + detailMessage);
	}

}