package com.BasicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCustomer {
    private ValidationHelper validationHelper;
    private CustomerRepository customerRepository;

    //Test localhost port
    @GetMapping("/hello")
    public @ResponseBody
    String show() {
        return "Hello";
    }

    /**
     * Receives all 3 params, checks for all errors
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        String address = customer.getAddress();
        String emailId = customer.getEmailId();
        String mobileNo = customer.getMobileNo();
        log.info("Trying to create user with email: {}, mobile: {}, address: {}", emailId, mobileNo, address);
        validationHelper.validAddress(address);
        validationHelper.validMobile(mobileNo);
        validationHelper.validEmailId(emailId);
        validationHelper.alreadyExistingMobile(mobileNo);
        validationHelper.alreadyExistingEmailId(emailId);
        customerRepository.save(customer);
        return ResponseEntity.status(200).body(ErrorCodes.SUCCESS_CODE);
    }
}
