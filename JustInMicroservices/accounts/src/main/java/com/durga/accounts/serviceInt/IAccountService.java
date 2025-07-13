package com.durga.accounts.serviceInt;

import com.durga.accounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);

    boolean updateAccountDetails(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
