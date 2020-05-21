package com.TestUtils;

import com.CustomerInfo.Customer;

public class TestHelper {
	public static final String VALID_MOBILE = "9898989898";
	public static final String VALID_ADDRESS = "Noida";
	public static final String VALID_MAIL = "me@gmail.com";
	public static final String INVALID_MOBILE = "9898989@!@";
	public static final String INVALID_MAIL = "me.com@gmail";
	public static final String INVALID_ADDRESS = "N@oida";
	public static final String NEW_ADDRESS = "Delhi";
	public static final String VALID_MAIL_2 = "me2@gmail.com";
	public static final String VALID_MOBILE_2 = "9190191001";

	public static Customer getAnyValidCustomer() {
		return new Customer(VALID_MOBILE, VALID_ADDRESS, VALID_MAIL);
	}

	public static Customer getCustomerWithInvalidMobile() {
		return new Customer(INVALID_MOBILE, VALID_ADDRESS, VALID_MAIL);
	}

	public static Customer getCustomerWithInvalidMail() {
		return new Customer(VALID_MOBILE, VALID_ADDRESS, INVALID_MAIL);
	}

	public static Customer getCustomerWithInvalidAddress() {
		return new Customer(VALID_MOBILE, INVALID_ADDRESS, VALID_MAIL);
	}

	public static Customer getCustomerWithValidMobileOrMailOnly(int type) {
		return (type == 1 ? new Customer(VALID_MOBILE, null, null) : new Customer(null, null, VALID_MAIL));
	}

	public static Customer getCustomerWithInValidMobileOrMailOnly(int type) {
		return (type == 1 ? new Customer(INVALID_MOBILE, null, null) : new Customer(null, null, INVALID_MAIL));
	}

	public static Customer getValidCustomerWithMobileANdAddress() {
		return new Customer(VALID_MOBILE, NEW_ADDRESS, null);
	}

	public static Customer getValidCustomerWithSameMobileAndDifferentMail() {
		return new Customer(VALID_MOBILE, VALID_ADDRESS, VALID_MAIL_2);
	}

	public static Customer getValidCustomerWithSameMailAndDifferentMobile() {
		return new Customer(VALID_MOBILE_2, VALID_ADDRESS, VALID_MAIL);
	}

}
