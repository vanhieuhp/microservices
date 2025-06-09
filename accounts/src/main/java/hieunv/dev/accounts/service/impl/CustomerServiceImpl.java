package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.CardsDto;
import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.LoansDto;
import hieunv.dev.accounts.entity.Account;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.exception.ResourceNotFoundException;
import hieunv.dev.accounts.mapper.AccountMapper;
import hieunv.dev.accounts.mapper.CustomerMapper;
import hieunv.dev.accounts.repository.AccountRepository;
import hieunv.dev.accounts.repository.CustomerRepository;
import hieunv.dev.accounts.service.client.CardsFeignClient;
import hieunv.dev.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        try {
            ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardsDetails(mobileNumber, correlationId);
            if (null != cardsDtoResponseEntity) {
                customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching cards details for mobileNumber: {} - {}", mobileNumber, e.getMessage());
        }

        try {
            ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber, correlationId);
            if (null != loansDtoResponseEntity) {
                customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching loans details for mobileNumber: {} - {}", mobileNumber, e.getMessage());
        }

        return customerDetailsDto;
    }
}
