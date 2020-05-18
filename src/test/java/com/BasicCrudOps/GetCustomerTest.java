package com.BasicCrudOps;


import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.UserNotFoundException;
import com.MainApplication.Application;
import com.TestUtils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreateCustomer.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class GetCustomerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @SpyBean
    private ValidationHelper validationHelper;
    @Autowired
    private MockMvc mvc;
    private boolean saved;

    @BeforeEach
    public void init__() {
        Customer validCustomer = TestHelper.getAnyValidCustomer();
        saved = false;
        Mockito.when(customerRepository.save(any(Customer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    saved = true;
                    return validCustomer;
                });
        Mockito.when(customerRepository.findByMobileNo(TestHelper.VALID_MOBILE))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (!saved) return null;
                    else return validCustomer;
                });
        Mockito.when(customerRepository.findByEmailId(TestHelper.VALID_MAIL))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (!saved) return null;
                    else return validCustomer;
                });
        Mockito.when(customerRepository.findByEmailId(TestHelper.INVALID_MAIL))
                .thenThrow(new UserNotFoundException(ErrorCodes.EMAIL_NOT_FOUND_INVALID_CODE));
        Mockito.when(customerRepository.findByMobileNo(TestHelper.INVALID_MOBILE))
                .thenThrow(new UserNotFoundException(ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE));
    }

    @Test
    public void testForAlreadySavedValidUser() throws Exception {
        // First create valid customer which calls save
        callCreate();
        assert (saved); //assert if actually saved
        MvcResult result = getMvcResult(1);
        assert (isEqual(result, TestHelper.getAnyValidCustomer()));
        assert (isStatusOk(result));
        result = getMvcResult(2);
        assert (isEqual(result, TestHelper.getAnyValidCustomer()));
        assert (isStatusOk(result));
        result = getMvcResult(3);
        assert (isEqual(result, ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE));
        assert (isStatusBadRequest(result));
        result = getMvcResult(4);
        assert isEqual(result, ErrorCodes.EMAIL_NOT_FOUND_INVALID_CODE);
        assert (isStatusBadRequest(result));
    }

    @Test
    public void testForUnsavedCustomer() throws Exception {
        MvcResult result = getMvcResult(1);
        assert (isEqual(result, ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE));
        assert (isStatusBadRequest(result));
        result = getMvcResult(2);
        assert (isEqual(result, ErrorCodes.EMAIL_NOT_FOUND_INVALID_CODE));
        assert (isStatusBadRequest(result));
    }

    private boolean isEqual(MvcResult actual, ErrorCodes.InternalHelper mobileNotFoundInvalidCode)
            throws UnsupportedEncodingException {
        return actual.getResponse().getContentAsString().equals(mobileNotFoundInvalidCode.toString());
    }

    private boolean isEqual(MvcResult actual, Customer expected)
            throws UnsupportedEncodingException {
        return actual.getResponse().getContentAsString().equals(expected.toString());
    }

    private void callCreate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/create")
                .content(TestHelper.getAnyValidCustomer().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    private MvcResult getMvcResult(int type) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/get")
                .content((type == 1 || type == 2) ? TestHelper.getCustomerWithValidMobileOrMailOnly(type).toString() :
                        TestHelper.getCustomerWithInValidMobileOrMailOnly(type - 2).toString())
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
