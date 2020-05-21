package com.ValidatorInterfaces;

import com.InvalidExceptions.InvalidFormatException;
import org.springframework.stereotype.Component;

import static com.Codes.ErrorEnums.*;

@Component
public class FormatValidator implements InvalidFormatValidator {

	public void isValidMobile(String mobileNo) {
		if (mobileNo != null && mobileNo.matches("\\d+") && mobileNo.length() == 10)
			return;
		throw new InvalidFormatException(MOBILE_INVALID_CODE);
	}

	public void isValidEmailId(String emailId) {
		if (emailId != null && emailId.matches(".*@(gmail|yahoo|protonmail|hotmail|rediffmail).com$"))
			return;
		throw new InvalidFormatException(EMAIL_INVALID_CODE);
	}

	public void isValidAddress(String address) {
		if (address != null && address.matches("^[a-zA-Z0-9,/ ]+$"))
			return;
		throw new InvalidFormatException(ADDRESS_INVALID_CODE);
	}

}
