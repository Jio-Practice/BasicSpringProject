package com.InvalidExceptions;

import com.Codes.ErrorEnums;

/**
 * Custom <u>unchecked</u> exception for error handling logic
 */

public class InvalidFormatException extends RuntimeException {
<<<<<<< HEAD
    public InvalidFormatException(ErrorCodes.InternalHelper internalHelper){
            super(internalHelper.toString());
    }
=======
	public InvalidFormatException(ErrorEnums e) {
		super(e.toString());
	}
>>>>>>> 1589371... Refactored src/java code and changed tests appropriately
}
