package com.MainApplication.SimplePostAndGet;

import com.CustomerInfo.Customer;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateCustomer {
    @RequestMapping(value = "/newCustomer", method = RequestMethod.POST)
    public @ResponseBody String createCustomer(@RequestBody Customer customer){
        return "Created new user with name- "+customer.getName();
    }
}
