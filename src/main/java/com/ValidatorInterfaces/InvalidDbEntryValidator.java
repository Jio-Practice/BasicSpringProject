package com.ValidatorInterfaces;

import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
public interface InvalidDbEntryValidator {

	void isAlreadyExistingMobile(@NonNull String mobileNo);

	void isAlreadyExistingEmailId(@NonNull String emailId);

	void isUserInDbWithMobile(@NonNull String mobileNo);

	void isUserInDbWithEmailId(@NonNull String emailId);
}
