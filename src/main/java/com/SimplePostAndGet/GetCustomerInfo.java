package com.SimplePostAndGet;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetCustomerInfo {
    @Autowired
    public final CustomerRepository customerRepository;
    @GetMapping("/customer/{mobileNo}")
    public @ResponseBody String getCustomerFromDB(@PathVariable String mobileNo){
        Customer customer = customerRepository.findByMobileNo(mobileNo);
        if(customer==null){
            return "Sorry, no customer with mobile number: "+mobileNo+" exists! ";
        }
        else{
            return String.format("Hi %s, Good to see you back!", customer.getName());
        }
    }
}
