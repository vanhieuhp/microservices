package hieunv.dev.accounts.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteCustomerCommand {

    @TargetAggregateIdentifier
    private final Long customerId;
    private final boolean activeSw;

}
