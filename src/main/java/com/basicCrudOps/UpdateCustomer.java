package com.basicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomer {
    private final CustomerRepository customerRepository;
    private final CheckError checkError;
    private String ADDRESS_INVALID_CODE="003";

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public @ResponseBody FormatMessage ChangeAddress(@RequestBody Customer customer,
                                                     HttpServletResponse response) {

        FormatMessage message = checkError.getMessage(customer, response);
        if(ADDRESS_INVALID_CODE.equals(message.getCode())) {
            return message;
        }
        if(customerRepository.findByMobileNo(customer.getMobileNo())==null) {
            response.setStatus(400);
            return new FormatMessage("006", "Mobile no. not found!");
        }
        customerRepository.updateCustomerAddress(customer.getAddress(), customer.getMobileNo());
        response.setStatus(200);
        return new FormatMessage("Success!");
    }
}