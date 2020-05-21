package com.BasicCrudOps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Codes.ErrorEnums;
import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.ValidatorInterfaces.InvalidDbEntryValidator;
import com.ValidatorInterfaces.InvalidFormatValidator;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCustomer {
<<<<<<< HEAD
    private ValidatorHelper validatorHelper;
    private CustomerRepository customerRepository;

    //Test localhost port
    @GetMapping("/hello")
    public @ResponseBody String show(){ return "Hello"; }

    /**
     * Receives all 3 params, checks for all errors
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> createCustomer(@RequestBody Customer customer){
        String address=customer.getAddress();
        String emailId=customer.getEmailId();
        String mobileNo=customer.getMobileNo();
        log.info("Trying to create user with email: {}, mobile: {}, address: {}", emailId, mobileNo, address);
        ValidatorHelper.validAddress(address);
        ValidatorHelper.validMobile(mobileNo);
        ValidatorHelper.validEmailId(emailId);
        validatorHelper.alreadyExistingMobile(mobileNo);
        validatorHelper.alreadyExistingEmailId(emailId);
        customerRepository.save(customer);
        return ResponseEntity.status(200).body(ErrorCodes.SUCCESS_CODE);

    }
=======
	private InvalidFormatValidator formatValidator;
	private InvalidDbEntryValidator dbEntryValidator;
	private CustomerRepository customerRepository;

	// Test localhost port
	@GetMapping("/hello")
	public @ResponseBody String show() {
		return "Hello";
	}

	/**
	 * Receives all 3 params, checks for all errors
	 */
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		String address = customer.getAddress();
		String emailId = customer.getEmailId();
		String mobileNo = customer.getMobileNo();
		formatValidator.isValidAddress(address);
		formatValidator.isValidMobile(mobileNo);
		formatValidator.isValidEmailId(emailId);
		dbEntryValidator.isAlreadyExistingMobile(mobileNo);
		dbEntryValidator.isAlreadyExistingEmailId(emailId);
		customerRepository.save(customer);
		return ResponseEntity.status(200).body(ErrorEnums.SUCCESS_CODE);
	}
>>>>>>> 1589371... Refactored src/java code and changed tests appropriately
}
