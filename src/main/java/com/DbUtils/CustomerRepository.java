package com.DbUtils;

import com.CustomerInfo.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByMobileNo(String mobileNo);
}
