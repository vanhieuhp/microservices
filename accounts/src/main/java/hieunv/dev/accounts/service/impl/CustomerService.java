package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.CustomerDto;
import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.entity.Customer;
import jakarta.validation.Valid;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

    CustomerDto fetchCustomer(String mobileNumber);

    void createCustomer(CustomerDto customerDto);

    boolean deleteCustomer(Long customerId);

    boolean updateCustomer(CustomerDto customerDto);

    boolean updateMobileNumber(MobileNumberUpdate mobileNumberUpdate);

}
