package hieunv.dev.accounts.command.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdatedEvent {

    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;
}
