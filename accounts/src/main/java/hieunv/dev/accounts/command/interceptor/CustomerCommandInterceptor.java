package hieunv.dev.accounts.command.interceptor;

import hieunv.dev.accounts.command.CreateCustomerCommand;
import hieunv.dev.accounts.command.DeleteCustomerCommand;
import hieunv.dev.accounts.command.UpdateCustomerCommand;
import hieunv.dev.accounts.constants.CustomerConstants;
import hieunv.dev.accounts.entity.Customer;
import hieunv.dev.accounts.exception.CustomerAlreadyExistsException;
import hieunv.dev.accounts.exception.ResourceNotFoundException;
import hieunv.dev.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerRepository customerRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateCustomerCommand.class.equals(command.getPayloadType())) {
                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> existingCustomer = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW);
                if (existingCustomer.isPresent()) {
                    throw new IllegalArgumentException("Customer with mobile number " + createCustomerCommand.getMobileNumber() + " already exists.");
                }
            } else if (UpdateCustomerCommand.class.equals(command.getPayloadType())) {
                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
                Customer existingCustomer = customerRepository.findById(updateCustomerCommand.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", updateCustomerCommand.getCustomerId().toString()));
                if (!existingCustomer.getMobileNumber().equals(updateCustomerCommand.getMobileNumber())) {
                    Optional<Customer> customerWithNewMobile = customerRepository.findByMobileNumberAndActiveSw(updateCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW);
                    if (customerWithNewMobile.isPresent()) {
                        throw new CustomerAlreadyExistsException("Customer with mobile number " + updateCustomerCommand.getMobileNumber() + " already exists.");
                    }
                }
            } else if (DeleteCustomerCommand.class.equals(command.getPayloadType())) {
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                customerRepository.findByIdAndActiveSw(deleteCustomerCommand.getCustomerId(), CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", deleteCustomerCommand.getCustomerId().toString()));

            }

            return command;
        };
    }
}
