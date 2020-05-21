package com.InvalidExceptions;

import com.Codes.ErrorEnums;

/**
 * Custom <u>unchecked</u> exception for error handling logic
 */
public class UserAlreadyExistingException extends RuntimeException {
	public UserAlreadyExistingException(ErrorEnums e) {
		super(e.toString());
	}
}
