package com.DbUtils;

import com.CustomerInfo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE CUSTOMER SET ADDRESS= ?1 WHERE MOBILE_NO= ?2", nativeQuery = true)
	void updateCustomerAddress(String address, String mobileNo);

<<<<<<< HEAD
    Customer findByEmailId(String emailId);
    Customer findByMobileNo(String mobileNo);
=======
	Customer findByEmailId(String emailId);

	Customer findByMobileNo(String mobileNo);
>>>>>>> 1589371... Refactored src/java code and changed tests appropriately
}
