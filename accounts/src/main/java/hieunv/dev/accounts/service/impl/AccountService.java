package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.entity.Account;

public interface AccountService {

    AccountDto createAccount(Account account);

    AccountDto fetchAccount(String mobileNumber);

    AccountDto fetchAccountById(Long accountNumber);

    boolean updateAccount(AccountDto accountDto);

    boolean deleteAccount(Long accountNumber);

    boolean updateMobileNumber(MobileNumberUpdate mobileNumberUpdate);

    boolean rollbackAccountMobileNumber(MobileNumberUpdate mobileNumberUpdate);
}
