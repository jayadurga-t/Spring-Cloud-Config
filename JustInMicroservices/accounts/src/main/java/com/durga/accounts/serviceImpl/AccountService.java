package com.durga.accounts.serviceImpl;

import com.durga.accounts.constants.AccountConstants;
import com.durga.accounts.dto.AccountDto;
import com.durga.accounts.dto.CustomerDto;
import com.durga.accounts.entity.Account;
import com.durga.accounts.entity.Customer;
import com.durga.accounts.exception.CustomerAlreadyExistsException;
import com.durga.accounts.exception.ResourceNotFoundException;
import com.durga.accounts.mapper.AccountMapper;
import com.durga.accounts.mapper.CustomerMapper;
import com.durga.accounts.repository.AccountRepository;
import com.durga.accounts.repository.CustomerRepository;
import com.durga.accounts.serviceInt.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(new Customer(), customerDto);
        Optional<Customer> optionalCustomer =customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "+customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    private Account createNewAccount(Customer savedCustomer) {
        Account account=new Account();
        account.setCustomerId(savedCustomer.getCustomerId());
        long randomAccountNumber=1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account=accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(new AccountDto(), account));
        return customerDto;
    }

    @Override
    public boolean updateAccountDetails(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountDto accountDto=customerDto.getAccountDto();
        if(accountDto!=null){
            Account account=accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccount(account, accountDto);
            accountRepository.save(account);

            Long customerId=account.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", customerDto.getMobileNumber())
            );
            CustomerMapper.mapToCustomer(customer, customerDto);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        customerRepository.deleteById(customer.getCustomerId());
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }
}
