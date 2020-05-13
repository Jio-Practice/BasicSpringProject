package com.basicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCustomer {
    private final CustomerRepository customerRepository;
    private final CheckError checkError;
    private final String SUCCESS="Success!";
    //Test localhost port
    @GetMapping("/hello")
    public @ResponseBody String show(){ return "Hello"; }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody FormatMessage createCustomer(@RequestBody Customer customer, HttpServletResponse response){
        FormatMessage formatMessage = checkError.getMessage(customer, response);
        if(SUCCESS.equals(formatMessage.getMessage())){
            if(alreadyExistingEmailId(customer.getEmailId())) {
                formatMessage =
                        new FormatMessage("004", "Email Id already in use");
            }
            if(alreadyExistingMobile(customer.getMobileNo())) {
                formatMessage =
                        new FormatMessage("005", "Mobile No. already in use");
            }
            if(SUCCESS.equals(formatMessage.getMessage())) {
                customerRepository.save(customer);
            }
        }
        return formatMessage;
    }

    private boolean alreadyExistingMobile(@NonNull String mobileNo){
        return customerRepository.findByMobileNo(mobileNo)!=null;
    }
    private boolean alreadyExistingEmailId(@NonNull String emailId){
        return customerRepository.findByEmailId(emailId)!=null;
    }

}
