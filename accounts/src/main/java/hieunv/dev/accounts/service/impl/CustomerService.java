package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @param correlationId - Header correlationId
     *  @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
