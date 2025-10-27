package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.constants.AccountConstants;
import hieunv.dev.accounts.constants.CustomerConstants;
import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.entity.Account;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.exception.AccountAlreadyExistsException;
import hieunv.dev.accounts.exception.ResourceNotFoundException;
import hieunv.dev.accounts.mapper.AccountMapper;
import hieunv.dev.accounts.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountsRepository;
    private final Random random = new Random();
    private final StreamBridge streamBridge;

    /**
     * Generates a unique 12-digit account number
     */
    private Long generateAccountNumber() {
        Long accountNumber;
        do {
            // Generate a 12-digit account number
            accountNumber = 100000000000L + random.nextLong(900000000000L);
        } while (accountsRepository.existsById(accountNumber));
        return accountNumber;
    }

    @Override
    public AccountDto createAccount(Account account) {
        Optional<Account> optionalAccount = accountsRepository.findByMobileNumberAndActiveSw(account.getMobileNumber(),
                AccountConstants.ACTIVE_SW);
        if (optionalAccount.isPresent()) {
            throw new AccountAlreadyExistsException("Account already registered with given mobileNumber " + account.getMobileNumber());
        }
        
        // Generate unique account number if not provided
        if (account.getAccountNumber() == null) {
            account.setAccountNumber(generateAccountNumber());
        }
        
        account.setActiveSw(AccountConstants.ACTIVE_SW);
        Account savedAccount = accountsRepository.save(account);
        log.info("Account created successfully with accountNumber: {}", savedAccount.getAccountNumber());
        
        return AccountMapper.mapToAccountDto(savedAccount, new AccountDto());
    }

    @Override
    public AccountDto fetchAccount(String mobileNumber) {
        Account account = accountsRepository.findByMobileNumberAndActiveSw(mobileNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
                );
        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());
        return accountDto;
    }

    @Override
    public AccountDto fetchAccountById(Long accountNumber) {
        Account account = accountsRepository.findByAccountNumberAndActiveSw(accountNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());
        return accountDto;
    }

    @Override
    public boolean updateAccount(AccountDto accountDto) {
        Account account = accountsRepository.findByAccountNumberAndActiveSw(accountDto.getAccountNumber(), AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()));
        
        AccountMapper.mapToAccount(accountDto, account);
        accountsRepository.save(account);
        log.info("Account updated successfully for accountNumber: {}", accountDto.getAccountNumber());
        return true;
    }

    @Override
    public boolean deleteAccount(Long accountNumber) {
        Account account = accountsRepository.findById(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
        );
        account.setActiveSw(AccountConstants.IN_ACTIVE_SW);
        accountsRepository.save(account);

        log.info("Account deleted successfully for accountNumber: {}", accountNumber);
        return true;
    }

    @Override
    @Transactional
    public boolean updateMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        boolean result = false;
        try {
            String currentMobileNumber = mobileNumberUpdate.getCurrentMobileNumber();
            Account account = accountsRepository.findByMobileNumberAndActiveSw(currentMobileNumber, AccountConstants.ACTIVE_SW)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", currentMobileNumber));
            account.setMobileNumber(mobileNumberUpdate.getNewMobileNumber());
            accountsRepository.save(account);
            updateCardMobileNumber(mobileNumberUpdate);
            result = true;
        } catch (Exception e) {
            log.error("Error occurred while updating mobile number: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rollbackCustomerMobileNumber(mobileNumberUpdate);
        }

        return result;
    }

    @Override
    public boolean rollbackAccountMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        String newMobileNumber = mobileNumberUpdate.getNewMobileNumber();
        Account account = accountsRepository.findByMobileNumberAndActiveSw(newMobileNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", newMobileNumber));
        account.setMobileNumber(mobileNumberUpdate.getCurrentMobileNumber());
        accountsRepository.save(account);
        rollbackCustomerMobileNumber(mobileNumberUpdate);
        return true;
    }

    public void updateCardMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        log.info("Sending updateCardMobileNumber request for the details: {}", mobileNumberUpdate);
        var result = streamBridge.send("updateCardMobileNumber-out-0", mobileNumberUpdate);
        log.info("Is the updateCardMobileNumber request sent successfully? : {}", result);
    }

    public void rollbackCustomerMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        log.info("Sending rollbackCustomerMobileNumber request for the details: {}", mobileNumberUpdate);
        var result = streamBridge.send("rollbackCustomerMobileNumber-out-0", mobileNumberUpdate);
        log.info("Is the rollbackCustomerMobileNumber request sent successfully? : {}", result);
    }
}
