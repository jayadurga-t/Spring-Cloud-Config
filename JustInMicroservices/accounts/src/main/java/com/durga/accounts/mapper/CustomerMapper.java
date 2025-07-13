package com.durga.accounts.mapper;

import com.durga.accounts.dto.CustomerDto;
import com.durga.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer){
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(Customer customer, CustomerDto customerDto){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
