package hieunv.dev.accounts.functions;

import hieunv.dev.accounts.service.impl.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;


@Configuration
public class AccountFunctions {

    private static final Logger log = LoggerFactory.getLogger(AccountFunctions.class);

    @Bean
//    public Function<Long, Long> updateCommunication(AccountService accountService) {
//        return accountNumber -> {
//            log.info("Updating communication for account number: {}", accountNumber);
//            accountService.updateCommunication(accountNumber);
//            return accountNumber;
//        };
//    }
    public Consumer<Long> updateCommunication(AccountService accountService) {
        return accountNumber -> {
            log.info("Updating communication for account number: {}", accountNumber);
            accountService.updateCommunication(accountNumber);
        };
    }
}
