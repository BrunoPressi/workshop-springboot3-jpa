package com.compass.uol.course.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.compass.uol.course.services.exception.DatabaseException;
import com.compass.uol.course.services.exception.InvalidPasswordException;
import com.compass.uol.course.services.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<StandardError> invalidPassword(ResponseStatusException e, HttpServletRequest request) {
		String error = "Auth error";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleMethodNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		String error = "Argument error";
		HttpStatus status = HttpStatus.BAD_REQUEST;		
		
	    ValidationError err = new ValidationError(
	            Instant.now(),
	            status.value(),
	            error,
	            "Erro de validação nos campos.",
	            request.getRequestURI()
	    );

	    e.getBindingResult().getFieldErrors().forEach(fieldError ->
	            err.addError(fieldError.getField(), fieldError.getDefaultMessage())
	    );
		
		return ResponseEntity.status(status).body(err);
	}
}
