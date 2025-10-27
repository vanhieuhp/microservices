package hieunv.dev.accounts.functions;

import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.service.impl.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


@Slf4j
@Configuration
public class AccountFunctions {

//    @Bean
//    public Function<Long, Long> updateCommunication(AccountService accountService) {
//        return accountNumber -> {
//            log.info("Updating communication for account number: {}", accountNumber);
//            accountService.updateCommunication(accountNumber);
//            return accountNumber;
//        };
//    }
//    public Consumer<Long> updateCommunication(AccountService accountService) {
//        return accountNumber -> {
//            log.info("Updating communication for account number: {}", accountNumber);
//            accountService.updateCommunication(accountNumber);
//        };
//    }

    @Bean
    public Consumer<MobileNumberUpdate> updateAccountMobileNumber(AccountService accountService) {
        return (mobileNumberUpdate) -> {
            log.info("Received updateAccountMobileNumber event: {}", mobileNumberUpdate);
            accountService.updateMobileNumber(mobileNumberUpdate);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdate> rollbackAccountMobileNumber(AccountService accountService) {
        return (mobileNumberUpdate) -> {
            log.info("Received rollbackAccountMobileNumber event: {}", mobileNumberUpdate);
            accountService.rollbackAccountMobileNumber(mobileNumberUpdate);
        };
    }

}
