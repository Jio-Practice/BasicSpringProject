package com.BasicCrudOps;

import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.InvalidFormatException;
import com.InvalidExceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ValidatorHelper {
    private CustomerRepository customerRepository;

    public static void validMobile(String mobileNo) throws InvalidFormatException {
        if(mobileNo.matches("\\d+")&&mobileNo.length()==10) return;
        throw new InvalidFormatException(ErrorCodes.MOBILE_INVALID_CODE);
    }
    public static void validEmailId(String emailId){
        if(emailId.matches(".*@(gmail|yahoo|protonmail|hotmail|rediffmail).com$")) return ;
        throw new InvalidFormatException(ErrorCodes.EMAIL_INVALID_CODE);
    }
    public static void validAddress(String address) {
        if(address.matches("^[a-zA-Z0-9,/ ]+$")) return;
        throw new InvalidFormatException(ErrorCodes.ADDRESS_INVALID_CODE);
    }
    public  void alreadyExistingMobile(@NonNull String mobileNo){
        log.info("Exists? "+customerRepository.findByMobileNo(mobileNo));
        if(customerRepository.findByMobileNo(mobileNo)==null) return;
        throw new UserNotFoundException(ErrorCodes.MOBILE_IN_USE_INVALID_CODE);
    }
    public  void alreadyExistingEmailId(@NonNull String emailId){
        if(customerRepository.findByEmailId(emailId)==null) return;
        throw new UserNotFoundException(ErrorCodes.EMAIL_IN_USE_INVALID_CODE);
    }
    public void checkForUserWithMobile(@NonNull String mobileNo){
        if(customerRepository.findByMobileNo(mobileNo)!=null) return;
        throw new UserNotFoundException(ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE);
    }
    public void checkForUserWithEmailId(@NonNull String emailId){
        if(customerRepository.findByEmailId(emailId)!=null) return;
        throw new UserNotFoundException(ErrorCodes.EMAIL_NOT_FOUND_INVALID_CODE);
    }
}
