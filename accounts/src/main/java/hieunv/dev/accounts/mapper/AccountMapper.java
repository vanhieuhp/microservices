package hieunv.dev.accounts.mapper;

import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        accountDto.setActiveSw(account.isActiveSw());
        accountDto.setMobileNumber(account.getMobileNumber());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        account.setActiveSw(accountDto.isActiveSw());
        account.setMobileNumber(accountDto.getMobileNumber());
        return account;
    }
}
