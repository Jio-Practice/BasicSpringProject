package com.basicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Component
public class CheckError {
    @Autowired
    private final CustomerRepository customerRepository;
    public FormatMessage getMessage(Customer customer, HttpServletResponse response){
        String mobileNo=customer.getMobileNo();
        String emailId=customer.getEmailId();
        String address=customer.getAddress();
        response.setStatus(400);

        if(address!=null&&!validAddress(address)) return new FormatMessage("003","Address invalid");
        if (mobileNo!=null&&!validMobile(mobileNo)) return new FormatMessage("001", "Mobile No. invalid");
        if (emailId!=null&&!validEmailId(emailId)) return new FormatMessage("002", "Email Id invalid");

        // Assuming not all are null and appropriately set as per request
        response.setStatus(200);
        return new FormatMessage("Success!");
    }
    private boolean validMobile(String mobileNo){
        return mobileNo.matches("\\d+")&&mobileNo.length()==10;
    }
    private boolean validEmailId(String emailId){
        return emailId.matches(".*@(gmail|yahoo|protonmail|hotmail|rediffmail).com$");
    }
    private boolean validAddress(String address) {
        return address.matches("^[a-zA-Z0-9,/ ]+$");
    }

}
