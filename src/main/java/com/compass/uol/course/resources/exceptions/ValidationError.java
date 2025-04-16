package com.compass.uol.course.resources.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private Map<String, String> errors = new HashMap<>();

	public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}

}
