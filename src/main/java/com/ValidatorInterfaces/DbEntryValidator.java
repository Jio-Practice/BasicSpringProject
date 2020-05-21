package com.ValidatorInterfaces;

import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.UserAlreadyExistingException;
import com.InvalidExceptions.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.Codes.ErrorEnums.*;

@Component
@RequiredArgsConstructor
public class DbEntryValidator implements InvalidDbEntryValidator {
	@Autowired
	private final CustomerRepository customerRepository;

	public void isAlreadyExistingMobile(@NonNull String mobileNo) {
		if (customerRepository.findByMobileNo(mobileNo) == null)
			return;
		throw new UserAlreadyExistingException(MOBILE_IN_USE_INVALID_CODE);
	}

	public void isAlreadyExistingEmailId(@NonNull String emailId) {
		if (customerRepository.findByEmailId(emailId) == null)
			return;
		throw new UserAlreadyExistingException(EMAIL_IN_USE_INVALID_CODE);
	}

	public void isUserInDbWithMobile(@NonNull String mobileNo) {
		if (customerRepository.findByMobileNo(mobileNo) != null)
			return;
		throw new UserNotFoundException(MOBILE_NOT_FOUND_INVALID_CODE);
	}

	public void isUserInDbWithEmailId(@NonNull String emailId) {
		if (customerRepository.findByEmailId(emailId) != null)
			return;
		throw new UserNotFoundException(EMAIL_NOT_FOUND_INVALID_CODE);
	}
}
