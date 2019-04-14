package com.shopping.cart.exception;

public class OrderQuantityExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderQuantityExceededException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderQuantityExceededException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public OrderQuantityExceededException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OrderQuantityExceededException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OrderQuantityExceededException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
