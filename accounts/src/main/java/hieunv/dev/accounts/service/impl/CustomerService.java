package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.CustomerDto;
import jakarta.validation.constraints.Pattern;

public interface CustomerService {

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @param correlationId - Header correlationId
     *  @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

    CustomerDto fetchCustomer(@Pattern(regexp="(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits") String mobileNumber);
}
