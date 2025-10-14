package hieunv.dev.accounts.query.projection;

import hieunv.dev.accounts.command.event.CustomerCreatedEvent;
import hieunv.dev.accounts.command.event.CustomerDeletedEvent;
import hieunv.dev.accounts.command.event.CustomerUpdatedEvent;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProjection {

    private final CustomerService customerService;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(event, customer);
        customerService.createCustomer(customer);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        customerService.updateCustomer(event);
    }

    @EventHandler
    public void deleteCustomer(CustomerDeletedEvent event) {
        customerService.deleteCustomer(event.getCustomerId());
    }
}
