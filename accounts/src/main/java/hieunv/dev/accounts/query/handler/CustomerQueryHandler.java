package hieunv.dev.accounts.query.handler;

import hieunv.dev.accounts.dto.CustomerDto;
import hieunv.dev.accounts.query.FindCustomerQuery;
import hieunv.dev.accounts.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final CustomerService customerService;

    @QueryHandler
    public CustomerDto findCustomer(FindCustomerQuery findCustomerQuery) {
        return customerService.fetchCustomer(findCustomerQuery.getMobileNumber());
    }
}
