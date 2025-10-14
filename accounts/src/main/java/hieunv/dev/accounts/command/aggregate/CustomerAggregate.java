package hieunv.dev.accounts.command.aggregate;

import hieunv.dev.accounts.command.CreateCustomerCommand;
import hieunv.dev.accounts.command.DeleteCustomerCommand;
import hieunv.dev.accounts.command.UpdateCustomerCommand;
import hieunv.dev.accounts.command.event.CustomerCreatedEvent;
import hieunv.dev.accounts.command.event.CustomerDeletedEvent;
import hieunv.dev.accounts.command.event.CustomerUpdatedEvent;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.exception.CustomerAlreadyExistsException;
import hieunv.dev.accounts.repository.CustomerRepository;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Optional;


@NoArgsConstructor
@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand, CustomerRepository customerRepository) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number: " + createCustomerCommand.getMobileNumber());
        }

        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);
        AggregateLifecycle.apply(customerCreatedEvent);
    }

    @EventSourcingHandler
    public void fon(CustomerCreatedEvent event) {
        this.customerId = event.getCustomerId();
        this.name = event.getName();
        this.email = event.getEmail();
        this.mobileNumber = event.getMobileNumber();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand command) {
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(command, customerUpdatedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent event) {
        this.name = event.getName();
        this.email = event.getEmail();
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand command) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(command, customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent event) {
        this.activeSw = event.isActiveSw();
    }
}
