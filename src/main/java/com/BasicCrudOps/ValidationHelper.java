package com.BasicCrudOps;

import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.InvalidFormatException;
import com.InvalidExceptions.UserAlreadyExistingException;
import com.InvalidExceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ValidationHelper {
    private CustomerRepository customerRepository;

    public void validMobile(String mobileNo) {
        if (mobileNo.matches("\\d+") && mobileNo.length() == 10) return;
        throw new InvalidFormatException(ErrorCodes.MOBILE_INVALID_CODE);
    }

    public void validEmailId(String emailId) {
        if (emailId.matches(".*@(gmail|yahoo|protonmail|hotmail|rediffmail).com$")) return;
        throw new InvalidFormatException(ErrorCodes.EMAIL_INVALID_CODE);
    }

    public void validAddress(String address) {
        if (address.matches("^[a-zA-Z0-9,/ ]+$")) return;
        throw new InvalidFormatException(ErrorCodes.ADDRESS_INVALID_CODE);
    }

    public void alreadyExistingMobile(@NonNull String mobileNo) {
        if (customerRepository.findByMobileNo(mobileNo) == null) return;
        throw new UserAlreadyExistingException(ErrorCodes.MOBILE_IN_USE_INVALID_CODE);
    }

    public void alreadyExistingEmailId(@NonNull String emailId) {
        if (customerRepository.findByEmailId(emailId) == null) return;
        throw new UserAlreadyExistingException(ErrorCodes.EMAIL_IN_USE_INVALID_CODE);
    }

    public void checkForUserWithMobile(@NonNull String mobileNo) {
        if (customerRepository.findByMobileNo(mobileNo) != null) return;
        throw new UserNotFoundException(ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE);
    }

    public void checkForUserWithEmailId(@NonNull String emailId) {
        if (customerRepository.findByEmailId(emailId) != null) return;
        throw new UserNotFoundException(ErrorCodes.EMAIL_NOT_FOUND_INVALID_CODE);
    }
}
