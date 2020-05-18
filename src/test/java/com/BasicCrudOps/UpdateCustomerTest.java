package com.BasicCrudOps;


import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
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
public class UpdateCustomerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @SpyBean
    private ValidationHelper validationHelper;
    @Autowired
    private MockMvc mvc;
    private boolean saved;
    private Customer validCustomer;

    @BeforeEach
    public void init__() {
        saved = false;
        validCustomer = TestHelper.getAnyValidCustomer();
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
        Mockito.doAnswer((InvocationOnMock invocation) -> {
            if (!saved) return null;
            else validCustomer.setAddress(TestHelper.NEW_ADDRESS);
            return null;
        }).when(customerRepository).updateCustomerAddress(TestHelper.NEW_ADDRESS, TestHelper.VALID_MOBILE);
    }

    @Test
    public void testForCorrectUpdateForExistingUser() throws Exception {
        callCreate();
        MvcResult result = getMvcResult();
        assert (isStatusOk(result));
        assert (isEquals(result, ErrorCodes.SUCCESS_CODE));
        assert (validCustomer.getAddress().equals(TestHelper.NEW_ADDRESS));
    }

    @Test
    public void testForCorrectUpdateForNonExistingUSer() throws Exception {
        MvcResult result = getMvcResult();
        assert (isStatusBadRequest(result));
        assert (isEquals(result, ErrorCodes.MOBILE_NOT_FOUND_INVALID_CODE));
        assert (validCustomer.getAddress().equals(TestHelper.VALID_ADDRESS));
    }

    private boolean isEquals(MvcResult result, ErrorCodes.InternalHelper mobileInvalidCode)
            throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString().equals(mobileInvalidCode.toString());
    }

    private void callCreate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/create")
                .content(TestHelper.getAnyValidCustomer().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    private MvcResult getMvcResult() throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/update")
                .content(TestHelper.getValidCustomerWithMobileANdAddress().toString())
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
