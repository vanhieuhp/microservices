package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.AccountDto;

public interface AccountService {

    void createAccount(String mobileNumber);

    AccountDto fetchAccount(String mobileNumber);

    boolean updateAccount(AccountDto accountDto);

    boolean deleteAccount(String mobileNumber);

    boolean updateCommunication(Long accountNumber);
}
