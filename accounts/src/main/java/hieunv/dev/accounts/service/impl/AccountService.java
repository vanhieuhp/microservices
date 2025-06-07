package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.CustomerDto;

public interface AccountService {

    /**
     * @param customerDto - CustomerDto object
     * */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
