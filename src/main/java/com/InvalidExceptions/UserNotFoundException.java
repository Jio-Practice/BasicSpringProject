package com.InvalidExceptions;

import com.Codes.ErrorEnums;

/**
 * Custom <u>unchecked</u> exception for error handling logic
 */
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(ErrorEnums e) {
		super(e.toString());
	}
}
