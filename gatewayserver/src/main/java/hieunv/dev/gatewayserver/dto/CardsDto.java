package hieunv.dev.gatewayserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardsDto {

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Card number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type can not be a null or empty")
    private String cardType; // CREDIT, DEBIT

    @NotEmpty(message = "Card provider can not be a null or empty")
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int amountUsed;

    @PositiveOrZero(message = "Available amount should be equal or greater than zero")
    private int availableAmount;

    private boolean activeSw;
}
