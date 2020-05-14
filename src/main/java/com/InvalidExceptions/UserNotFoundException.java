package com.InvalidExceptions;

import com.BasicCrudOps.ErrorCodes;
import org.springframework.stereotype.Component;

/**
 * Custom <u>unchecked</u> exception for error handling logic
 */
@Component
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(ErrorCodes.InternalHelper internalHelper) {
        super(internalHelper.toString());
    }
}

