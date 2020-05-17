package com.BasicCrudOps;

import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.InvalidFormatException;
import com.InvalidExceptions.UserAlreadyExistingException;
import com.MainApplication.Application;
import com.TestUtils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * TODO; consider refactoring this
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CreateCustomer.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class CreateCustomerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private ValidationHelper validationHelper;
    @Autowired
    private MockMvc mvc;


    @BeforeEach
    public void init__() {

        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        // Invalid Format Exceptions
        doThrow(new InvalidFormatException(ErrorCodes.MOBILE_INVALID_CODE))
                .when(validationHelper).validMobile(TestHelper.INVALID_MOBILE);
        doThrow(new InvalidFormatException(ErrorCodes.EMAIL_INVALID_CODE))
                .when(validationHelper).validEmailId(TestHelper.INVALID_MAIL);
        doThrow(new InvalidFormatException(ErrorCodes.ADDRESS_INVALID_CODE))
                .when(validationHelper).validAddress(TestHelper.INVALID_ADDRESS);
        Mockito.doNothing().when(validationHelper).validMobile(TestHelper.VALID_MOBILE);
        Mockito.doNothing().when(validationHelper).validEmailId(TestHelper.VALID_MAIL);
        Mockito.doNothing().when(validationHelper).validAddress(TestHelper.VALID_ADDRESS);

    }

    @Test
    public void testForCreateCustomerWithValidEntries() throws Exception {
        MvcResult result = getMvcResult(TestHelper.getAnyValidCustomer());
        assert (isEquals(result, ErrorCodes.SUCCESS_CODE));
        assert (isStatusOk(result));
    }

    @Test
    public void testForDuplicateMobileOrMailException() throws Exception {

        doThrow(new UserAlreadyExistingException(ErrorCodes.EMAIL_IN_USE_INVALID_CODE))
                .when(validationHelper).alreadyExistingEmailId(any(String.class));

        MvcResult result = getMvcResult(TestHelper.getAnyValidCustomer());
        assert (isEquals(result, ErrorCodes.EMAIL_IN_USE_INVALID_CODE));
        assert (isStatusBadRequest(result));

        doThrow(new UserAlreadyExistingException(ErrorCodes.MOBILE_IN_USE_INVALID_CODE))
                .when(validationHelper).alreadyExistingMobile(any(String.class));

        result = getMvcResult(TestHelper.getAnyValidCustomer());
        assert (isEquals(result, ErrorCodes.MOBILE_IN_USE_INVALID_CODE));
        assert (isStatusBadRequest(result));
    }

    @Test
    public void testIfThrowsErrorOnAllInvalidFormats() throws Exception {
        MvcResult result = getMvcResult(TestHelper.getCustomerWithInvalidAddress());
        assert (isEquals(result, ErrorCodes.ADDRESS_INVALID_CODE));
        assert (isStatusBadRequest(result));
        result = getMvcResult(TestHelper.getCustomerWithInvalidMail());
        assert (isEquals(result, ErrorCodes.EMAIL_INVALID_CODE));
        assert (isStatusBadRequest(result));
        result = getMvcResult(TestHelper.getCustomerWithInvalidMobile());
        assert isEquals(result, ErrorCodes.MOBILE_INVALID_CODE);
        assert (isStatusBadRequest(result));
    }

    private boolean isEquals(MvcResult result, ErrorCodes.InternalHelper mobileInvalidCode)
            throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString().equals(mobileInvalidCode.toString());
    }

    private MvcResult getMvcResult(Customer customer) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/create")
                .content(customer.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private boolean isStatusOk(MvcResult result) {
        return (result.getResponse().getStatus() == 200);
    }

    private boolean isStatusBadRequest(MvcResult result) {
        return (result.getResponse().getStatus() == 400);
    }


}
