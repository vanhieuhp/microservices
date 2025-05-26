package hieunv.dev.microservices.service;

import hieunv.dev.microservices.dto.CustomerDto;

public interface AccountService {

    /**
     * @param customerDto - CustomerDto object
     * */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
