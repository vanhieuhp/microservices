package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.command.event.CustomerUpdatedEvent;
import hieunv.dev.accounts.constants.CustomerConstants;
import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.CardsDto;
import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.CustomerDto;
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
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Account account = accountRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
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
        log.info("Customer details fetched successfully");
        return customerDetailsDto;
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, CustomerConstants.ACTIVE_SW).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);

        return customerDto;

    }

    @Override
    public void createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumberAndActiveSw(customer.getMobileNumber(), CustomerConstants.ACTIVE_SW);
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer with mobile number " + customer.getMobileNumber() + " already exists.");
        }
        customerRepository.save(customer);
        log.info("Customer created successfully");
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerUpdatedEvent.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerUpdatedEvent.getCustomerId().toString()));
        CustomerMapper.mapEventToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        log.info("Customer updated successfully");
        return true;
    }

    @Override
    public boolean deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        log.info("Customer deleted successfully");
        return true;
    }
}
