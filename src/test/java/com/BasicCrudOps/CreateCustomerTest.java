package com.BasicCrudOps;

import com.Codes.ErrorEnums;
import com.CustomerInfo.Customer;
import com.DbUtils.CustomerRepository;
import com.InvalidExceptions.InvalidFormatException;
import com.InvalidExceptions.UserAlreadyExistingException;
import com.MainApplication.Application;
import com.TestUtils.TestHelper;
import com.ValidatorInterfaces.DbEntryValidator;
import com.ValidatorInterfaces.FormatValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
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

import static com.Codes.ErrorEnums.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * TODO; consider refactoring this
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class CreateCustomerTest {

	boolean saved;
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private FormatValidator validationHelper;
	@MockBean
	private DbEntryValidator dbEntryValidator;
	@Autowired
	private MockMvc mvc;
	private Customer validCustomer = TestHelper.getAnyValidCustomer();

	@BeforeEach
	public void init__() {
		saved = false;
		Mockito.when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
			saved = true;
			return validCustomer;
		});
		Mockito.doNothing().when(validationHelper).isValidMobile(TestHelper.VALID_MOBILE);
		Mockito.doNothing().when(validationHelper).isValidEmailId(TestHelper.VALID_MAIL);
		Mockito.doNothing().when(validationHelper).isValidAddress(TestHelper.VALID_ADDRESS);

		// Invalid Format Exceptions
		doThrow(new InvalidFormatException(MOBILE_INVALID_CODE)).when(validationHelper)
				.isValidMobile(TestHelper.INVALID_MOBILE);
		doThrow(new InvalidFormatException(EMAIL_INVALID_CODE)).when(validationHelper)
				.isValidEmailId(TestHelper.INVALID_MAIL);
		doThrow(new InvalidFormatException(ADDRESS_INVALID_CODE)).when(validationHelper)
				.isValidAddress(TestHelper.INVALID_ADDRESS);

		// Db validator exceptions
		Mockito.doAnswer((InvocationOnMock invocation) -> {
			if (!saved || !invocation.getArgument(0).toString().equals(TestHelper.VALID_MAIL))
				return validCustomer;
			else
				throw new UserAlreadyExistingException(EMAIL_IN_USE_INVALID_CODE);
		}).when(dbEntryValidator).isAlreadyExistingEmailId(any(String.class));

		Mockito.doAnswer((InvocationOnMock invocation) -> {
			if (!saved || !invocation.getArgument(0).toString().equals(TestHelper.VALID_MOBILE))
				return validCustomer;
			else
				throw new UserAlreadyExistingException(MOBILE_IN_USE_INVALID_CODE);
		}).when(dbEntryValidator).isAlreadyExistingMobile(any(String.class));

	}

	@Test
	public void testForCreateCustomerWithValidEntries() throws Exception {
		MvcResult result = getMvcResult(validCustomer);
		assert (isEquals(result, SUCCESS_CODE));
		assert (isStatusOk(result));
	}

	@Test
	public void testForDuplicateMobileOrMailException() throws Exception {

		getMvcResult(validCustomer);
		MvcResult result = getMvcResult(TestHelper.getValidCustomerWithSameMobileAndDifferentMail());
		assert (isEquals(result, MOBILE_IN_USE_INVALID_CODE));
		assert (isStatusBadRequest(result));

		result = getMvcResult(TestHelper.getValidCustomerWithSameMailAndDifferentMobile());
		assert (isEquals(result, EMAIL_IN_USE_INVALID_CODE));
		assert (isStatusBadRequest(result));
	}

	@Test
	public void testIfThrowsErrorOnAllInvalidFormats() throws Exception {
		MvcResult result = getMvcResult(TestHelper.getCustomerWithInvalidAddress());
		assert (isEquals(result, ADDRESS_INVALID_CODE));
		assert (isStatusBadRequest(result));
		result = getMvcResult(TestHelper.getCustomerWithInvalidMail());
		assert (isEquals(result, EMAIL_INVALID_CODE));
		assert (isStatusBadRequest(result));
		result = getMvcResult(TestHelper.getCustomerWithInvalidMobile());
		assert isEquals(result, MOBILE_INVALID_CODE);
		assert (isStatusBadRequest(result));
	}

	private boolean isEquals(MvcResult result, ErrorEnums mobileInvalidCode) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString().equals(mobileInvalidCode.toString());
	}

	private MvcResult getMvcResult(Customer customer) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.post("/create").content(customer.toString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
	}

	private boolean isStatusOk(MvcResult result) {
		return (result.getResponse().getStatus() == 200);
	}

	private boolean isStatusBadRequest(MvcResult result) {
		return (result.getResponse().getStatus() == 400);
	}

}
