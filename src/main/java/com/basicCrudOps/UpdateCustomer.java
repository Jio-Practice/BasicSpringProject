package com.basicCrudOps;

import com.DbUtils.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UpdateCustomer {
    @Autowired
    private final CustomerRepository customerRepository;

    @PostMapping("/customer/{mobileNo}/changeAddress")
    public @ResponseBody String ChangeAddress(@PathVariable String mobileNo,
                                              @RequestBody String newAddress){
        boolean valid = CheckIfExists(mobileNo);
        if(!valid) return "Not found any customer for specified mobile No";
        customerRepository.updateCustomerAddress(newAddress,mobileNo);
        return "Successfully changed your address!";
    }

    @PostMapping("/customer/{mobileNo}/changeName")
    public @ResponseBody String ChangeName(@PathVariable String mobileNo,
                                            @RequestBody String newName){
        boolean valid = CheckIfExists(mobileNo);
        if(!valid) return "Not found any customer for specified mobile No";
        customerRepository.updateCustomerName(newName,mobileNo);
        return "Successfully changed your name!";
    }

    boolean CheckIfExists(String mobileNo){
        return customerRepository.findByMobileNo(mobileNo)!=null;
    }
}
