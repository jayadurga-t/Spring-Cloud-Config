package com.durga.accounts.mapper;

import com.durga.accounts.dto.AccountDto;
import com.durga.accounts.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(AccountDto accountDto, Account account){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account mapToAccount(Account account, AccountDto accountDto){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
