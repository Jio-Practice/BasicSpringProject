package com.CustomerInfo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Customer {
    private String mobileNo;
    private String address;
    private String Name;
}
