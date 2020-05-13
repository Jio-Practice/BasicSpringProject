package com.basicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GetCustomerInfo {
    public final CustomerRepository customerRepository;
    private final CheckError checkError;
    @PostMapping(value = "/get", consumes = "application/json", produces = "application/json")
    public @ResponseBody Object getCustomerFromDB(@RequestBody Customer customer, HttpServletResponse response) {
        FormatMessage formatMessage = checkError.getMessage(customer, response);
        if (!formatMessage.getMessage().equals("Success!")) return formatMessage;
        String emailId = customer.getEmailId();
        String mobileNo = customer.getMobileNo();
        if (emailId != null) return customerRepository.findByEmailId(emailId);
        else return customerRepository.findByMobileNo(mobileNo);
    }
}
