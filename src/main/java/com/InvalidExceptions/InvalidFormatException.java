package com.InvalidExceptions;

import com.Codes.ErrorEnums;

/**
 * Custom <u>unchecked</u> exception for error handling logic
 */

public class InvalidFormatException extends RuntimeException {
	public InvalidFormatException(ErrorEnums e) {
		super(e.toString());
	}
}
