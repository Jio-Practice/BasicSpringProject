package com.DbUtils;


import com.CustomerInfo.Customer;
import com.MainApplication.Application;
import com.TestUtils.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
        assert (customerRepository.findByEmailId(TestHelper.VALID_MAIL) == null);
        assert (customerRepository.findByMobileNo(TestHelper.VALID_MOBILE) == null);
        customerRepository.save(TestHelper.getAnyValidCustomer());
        Customer customerFromRepository = customerRepository.findByEmailId(TestHelper.VALID_MAIL);
        assert (customerFromRepository != null);
        assert (customerFromRepository.toString().equals(TestHelper.getAnyValidCustomer().toString()));
        customerFromRepository = customerRepository.findByMobileNo(TestHelper.VALID_MOBILE);
        assert (customerFromRepository != null);
        assert (customerFromRepository.toString().equals(TestHelper.getAnyValidCustomer().toString()));

    }
}
