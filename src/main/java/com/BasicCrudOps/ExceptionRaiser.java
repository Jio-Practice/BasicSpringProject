package com.BasicCrudOps;

import com.InvalidExceptions.InvalidFormatException;
import com.InvalidExceptions.UserAlreadyExistingException;
import com.InvalidExceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionRaiser {

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseEntity<String> handleInvalidFromatException(InvalidFormatException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler({UserAlreadyExistingException.class})
    public ResponseEntity<String> handleAlreadyExistingException(UserAlreadyExistingException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
