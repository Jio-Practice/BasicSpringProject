package com.BasicCrudOps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodes {
    public static ErrorCodes.InternalHelper MOBILE_INVALID_CODE = new ErrorCodes.InternalHelper("1", "Mobile No. is invalid!");
    public static ErrorCodes.InternalHelper ADDRESS_INVALID_CODE = new ErrorCodes.InternalHelper("2", "Address is invalid!");
    public static ErrorCodes.InternalHelper EMAIL_INVALID_CODE = new ErrorCodes.InternalHelper("3", "Email Id is invalid!");
    public static ErrorCodes.InternalHelper MOBILE_IN_USE_INVALID_CODE = new ErrorCodes.InternalHelper("4", "Mobile No. already in use");
    public static ErrorCodes.InternalHelper EMAIL_IN_USE_INVALID_CODE = new ErrorCodes.InternalHelper("5", "Email already in use");
    public static ErrorCodes.InternalHelper MOBILE_NOT_FOUND_INVALID_CODE = new ErrorCodes.InternalHelper("6", "No Mobile No. found");
    public static ErrorCodes.InternalHelper EMAIL_NOT_FOUND_INVALID_CODE = new ErrorCodes.InternalHelper("7", "No EmailId found");
    public static ErrorCodes.InternalHelper SUCCESS_CODE = new ErrorCodes.InternalHelper("Success!");


    @Getter
    public static class InternalHelper {
        String code;
        String details;
        String message;

        InternalHelper(String code, String details) {
            this.code = code;
            this.details = details;
            this.message = "Validation Failed";
        }

        InternalHelper(String message) {
            this.message = message;
        }

        public String toString() {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
