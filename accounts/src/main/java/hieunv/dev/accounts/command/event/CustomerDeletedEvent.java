package hieunv.dev.accounts.command.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDeletedEvent {

    private Long customerId;
    private boolean activeSw;
}
