package hieunv.dev.loans.function;

import hieunv.dev.commonlib.dto.MobileNumberUpdate;
import hieunv.dev.loans.service.ILoansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class LoanFunction {

    @Bean
    public Consumer<MobileNumberUpdate> updateLoanMobileNumber(ILoansService loansService) {
        return (mobileNumberUpdate) -> {
            log.info("Received updateLoanMobileNumber event: {}", mobileNumberUpdate);
            loansService.updateLoanMobileNumber(mobileNumberUpdate);
        };
    }
}
