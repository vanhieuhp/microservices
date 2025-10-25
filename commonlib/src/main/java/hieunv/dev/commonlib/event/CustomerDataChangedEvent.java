package hieunv.dev.commonlib.event;

import lombok.Data;

@Data
public class CustomerDataChangedEvent {

    private String name;
    private String mobileNumber;
    private boolean activeSw;
}
