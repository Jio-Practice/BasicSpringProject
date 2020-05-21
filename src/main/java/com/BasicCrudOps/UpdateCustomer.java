package com.BasicCrudOps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Codes.ErrorEnums;
import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.ValidatorInterfaces.InvalidDbEntryValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomer {
	private final InvalidDbEntryValidator dbEntryValidator;
	private final CustomerRepository customerRepository;

	/**
	 * Receives mobile and new address and changes address if mobileNo. exists in DB
	 */
	@PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<Object> ChangeAddress(@RequestBody Customer customer) {
		dbEntryValidator.isUserInDbWithMobile(customer.getMobileNo());
		customerRepository.updateCustomerAddress(customer.getAddress(), customer.getMobileNo());
		return ResponseEntity.status(200).body(ErrorEnums.SUCCESS_CODE);
	}
}