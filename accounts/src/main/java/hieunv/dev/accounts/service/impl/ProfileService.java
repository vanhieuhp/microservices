package hieunv.dev.accounts.service.impl;

import hieunv.dev.accounts.dto.ProfileDto;
import hieunv.dev.commonlib.event.AccountDataChangedEvent;
import hieunv.dev.commonlib.event.CardDataChangedEvent;
import hieunv.dev.commonlib.event.CustomerDataChangedEvent;
import hieunv.dev.commonlib.event.LoanDataChangedEvent;

public interface ProfileService {

    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);
    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);
    void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent);
    void handleCardDataChangedEvent(CardDataChangedEvent customerDataChangedEvent);
    ProfileDto fetchProfile(String mobileNumber);
}
