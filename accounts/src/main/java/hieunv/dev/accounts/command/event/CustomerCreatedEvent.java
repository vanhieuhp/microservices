package hieunv.dev.accounts.command.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* NOUN + VERB(PastTense) + Event
* */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {

    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;
}
