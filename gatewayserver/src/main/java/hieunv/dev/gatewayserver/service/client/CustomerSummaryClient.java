package hieunv.dev.gatewayserver.service.client;

import hieunv.dev.gatewayserver.dto.AccountsDto;
import hieunv.dev.gatewayserver.dto.CardsDto;
import hieunv.dev.gatewayserver.dto.CustomerDto;
import hieunv.dev.gatewayserver.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {

    @GetExchange(value = "/easybank/accounts/api/customers/fetch", accept = "application/json")
    Mono<ResponseEntity<CustomerDto>> fetchCustomerDetails(@RequestParam("mobileNumber") String mobileNumber);


    @GetExchange(value = "/easybank/accounts/api/accounts/fetch", accept = "application/json")
    Mono<ResponseEntity<AccountsDto>> fetchAccountDetails(@RequestParam("mobileNumber") String mobileNumber);


    @GetExchange(value = "/easybank/loans/api/fetch", accept = "application/json")
    Mono<ResponseEntity<LoansDto>> fetchLoanDetails(@RequestParam("mobileNumber") String mobileNumber);


    @GetExchange(value = "/easybank/cards/api/fetch", accept = "application/json")
    Mono<ResponseEntity<CardsDto>> fetchCardDetails(@RequestParam("mobileNumber") String mobileNumber);
}
