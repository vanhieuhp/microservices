package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
