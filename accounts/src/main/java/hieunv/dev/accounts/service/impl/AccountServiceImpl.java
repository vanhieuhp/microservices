package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.constants.AccountConstants;
import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.AccountMsgDto;
import hieunv.dev.accounts.entity.Account;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.exception.ResourceNotFoundException;
import hieunv.dev.accounts.mapper.AccountMapper;
import hieunv.dev.accounts.repository.AccountRepository;
import hieunv.dev.accounts.repository.CustomerRepository;
import hieunv.dev.accounts.util.GeneratorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final StreamBridge streamBridge;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, StreamBridge streamBridge) {
        this.accountRepository = accountRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public void createAccount(String mobileNumber) {
        Account account = createNewAccount(mobileNumber);
        accountRepository.save(account);
        log.info("Account created successfully: {}", account);
    }

    private void sendCommunication(Account account, Customer customer) {
        var accountMsgDto = new AccountMsgDto(customer.getEmail(), customer.getName(),
                customer.getMobileNumber(), account.getAccountNumber());
        log.info("Sending account created message to communication service: {}", accountMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountMsgDto);
        log.info("Result of sending account created message to communication service: {}", result);
    }

    @Override
    public AccountDto fetchAccount(String mobileNumber) {
        Account account = accountRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
        );
        return AccountMapper.mapToAccountDto(account, new AccountDto());
    }

    @Override
    public boolean updateAccount(AccountDto accountDto) {
        boolean isUpdated = false;
        if (accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
                    );

            AccountMapper.mapToAccount(accountDto, account);
            accountRepository.save(account);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Account account = accountRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
        );
        accountRepository.delete(account);
        return true;
    }

    @Override
    public boolean updateCommunication(Long accountNumber) {
        boolean isUpdated = false;
//        if (accountNumber != null) {
//            Account accounts = accountRepository.findById(accountNumber).orElseThrow(
//                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
//            );
//            accounts.setCommunicationSw(true);
//            accountRepository.save(accounts);
//            isUpdated = true;
//        }
        return isUpdated;
    }

    private Account createNewAccount(String mobileNumber) {
        Account newAccount = new Account();
        long randomAccNumber = GeneratorUtils.generateAccountNumber();
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setMobileNumber(mobileNumber);
        newAccount.setActiveSw(true);
        return newAccount;
    }
}
