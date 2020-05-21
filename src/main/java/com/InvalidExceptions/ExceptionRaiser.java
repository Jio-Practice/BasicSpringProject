package com.InvalidExceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Codes.ErrorEnums;

@ControllerAdvice
public class ExceptionRaiser extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(400).body(ErrorEnums.MEDIA_FORMAT_INVALID_CODE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(400).body(ErrorEnums.JSON_INVALID_CODE);
	}

	@ExceptionHandler({ InvalidFormatException.class })
	public ResponseEntity<String> handleInvalidFromatException(InvalidFormatException e) {
		return getResponseEntityWithBody(e.getMessage());
	}

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<String> handleNotFoundException(UserNotFoundException e) {
		return getResponseEntityWithBody(e.getMessage());
	}

	@ExceptionHandler({ UserAlreadyExistingException.class })
	public ResponseEntity<String> handleAlreadyExistingException(UserAlreadyExistingException e) {
		return getResponseEntityWithBody(e.getMessage());
	}

	private ResponseEntity<String> getResponseEntityWithBody(String message) {
		return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(message);
	}

}
