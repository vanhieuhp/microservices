package hieunv.dev.accounts.command;

/*
* VERB + NOUN + Command
* */

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCustomerCommand {

    @TargetAggregateIdentifier
    private final Long customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final boolean activeSw;
}
