package dev.bhardwaj.food_order.exception;

public class DoesNotExistException extends RuntimeException {
	public DoesNotExistException(String message) {
		super(message);
	}
}
