package com.compass.uol.course.services.exception;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidPasswordException() {
		super("Invalid password");
	}

}
