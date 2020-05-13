package com.SimplePostAndGet;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
public class CreateCustomer {
    @Autowired
    private final CustomerRepository customerRepository;
    @RequestMapping(value = "/newCustomer", method = RequestMethod.POST)
    public @ResponseBody String createCustomer(@RequestBody Customer customer){
        log.info("Creating a user with name: {}, mobile: {}", customer.getName(), customer.getMobileNo());
        try{
            customerRepository.save(customer);
            return "Added customer with name" + customer.getName() +
                    "and mobileNo. "+customer.getMobileNo()+" to DB!";
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }

    //Test localhost port
    @GetMapping("/hello")
    public @ResponseBody String show(){ return "Hello"; }
}
