package hieunv.dev.accounts.functions;

import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.service.impl.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CustomerFunctions {

    @Bean
    public Consumer<MobileNumberUpdate> updateMobileNumberStatus() {
        return (mobileNumberUpdate) -> {
            log.info("Received updateCustomerMobileNumber event: {}", mobileNumberUpdate);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdate> rollbackCustomerMobileNumber(CustomerService customerService) {
        return (mobileNumberUpdate) -> {
            log.info("Received rollbackCustomerMobileNumber event: {}", mobileNumberUpdate);
            customerService.rollbackCustomerMobileNumber(mobileNumberUpdate);
        };
    }
}
