package com.BasicCrudOps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.ValidatorInterfaces.InvalidDbEntryValidator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GetCustomer {
	private final InvalidDbEntryValidator dbEntryValidator;
	private final CustomerRepository customerRepository;

	/**
	 * Receives either mobile or email and returns message accordingly
	 */
	@PostMapping(value = "/get", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<Object> getCustomerFromDB(@RequestBody Customer customer) {
		String mobileNo = customer.getMobileNo();
		String emailId = customer.getEmailId();
		if (mobileNo != null)
			dbEntryValidator.isUserInDbWithMobile(mobileNo);
		else
			dbEntryValidator.isUserInDbWithEmailId(emailId);
		return ResponseEntity.status(200).body(mobileNo != null ? customerRepository.findByMobileNo(mobileNo)
				: customerRepository.findByEmailId(emailId));

	}
}
