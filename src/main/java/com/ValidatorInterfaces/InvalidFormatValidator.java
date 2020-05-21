package com.ValidatorInterfaces;

import org.springframework.stereotype.Component;

@Component
public interface InvalidFormatValidator {

	void isValidMobile(String mobileNo);

	void isValidEmailId(String emailId);

	void isValidAddress(String address);
}
