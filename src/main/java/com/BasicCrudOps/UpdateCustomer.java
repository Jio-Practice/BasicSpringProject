package com.BasicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomer {
    private final ValidationHelper validationHelper;
    private final CustomerRepository customerRepository;


    /**
     * Receives mobile and new address and changes address if mobileNo. exists in DB
     */
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<Object> ChangeAddress(@RequestBody Customer customer) {
        validationHelper.checkForUserWithMobile(customer.getMobileNo());
        customerRepository.updateCustomerAddress(customer.getAddress(), customer.getMobileNo());
        return ResponseEntity.status(200).body(ErrorCodes.SUCCESS_CODE);
    }
}