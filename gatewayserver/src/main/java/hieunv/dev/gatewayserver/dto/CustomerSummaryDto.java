package hieunv.dev.gatewayserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerSummaryDto {

    private CustomerDto customer;
    private AccountsDto account;
    private CardsDto card;
    private LoansDto loan;
}
