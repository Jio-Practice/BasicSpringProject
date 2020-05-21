package com.DbUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.CustomerInfo.Customer;
import com.MainApplication.Application;
import com.TestUtils.TestHelper;

/**
 * Test Data storage layer with @DataJpaTest
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = Application.class)
public class CustomerRespositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testForCorrectDataInDb() {
		// check if null for empty db
		assert (customerRepository.findByEmailId(TestHelper.VALID_MAIL) == null);
		assert (customerRepository.findByMobileNo(TestHelper.VALID_MOBILE) == null);

		// save a new user
		customerRepository.save(TestHelper.getAnyValidCustomer());

		// check for correct user in db
		Customer customerFromRepository = customerRepository.findByEmailId(TestHelper.VALID_MAIL);
		assert (customerFromRepository != null);
		assert isEquals(customerFromRepository);
		customerFromRepository = customerRepository.findByMobileNo(TestHelper.VALID_MOBILE);
		assert (customerFromRepository != null);
		assert (isEquals(customerFromRepository));
		customerFromRepository = customerRepository.findByEmailId(TestHelper.INVALID_MAIL);
		assert (customerFromRepository == null);
		customerFromRepository = customerRepository.findByMobileNo(TestHelper.INVALID_MOBILE);
		assert (customerFromRepository == null);

		// check if updated correctly
		customerRepository.updateCustomerAddress(TestHelper.NEW_ADDRESS, TestHelper.VALID_MOBILE);
		customerFromRepository = customerRepository.findByMobileNo(TestHelper.VALID_MOBILE);
		assert (customerFromRepository.getAddress().equals(TestHelper.NEW_ADDRESS));
	}

	private boolean isEquals(Customer customerFromRepository) {
		return customerFromRepository.toString().equals(TestHelper.getAnyValidCustomer().toString());
	}
}
