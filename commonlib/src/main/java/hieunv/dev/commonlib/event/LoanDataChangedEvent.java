package hieunv.dev.commonlib.event;

import lombok.Data;

@Data
public class LoanDataChangedEvent {

    private String mobileNumber;
    private Long loanNumber;
}
