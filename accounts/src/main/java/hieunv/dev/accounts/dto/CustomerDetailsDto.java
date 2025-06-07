package hieunv.dev.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer", example = "John Doe"
    )
    @NotEmpty(message = "Mobile number must not be empty")
    @Size(min = 5, max=30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer", example = "john.doe@example.com"
    )
    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @Schema(
            description = "Mobile number of the customer", example = "1234567890"
    )
    @Pattern(regexp = "^$|[0-9]{10}", message = "AccountNumber must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account information of the customer"
    )
    @NotEmpty(message = "Account number must not be empty")
    private AccountDto accountDto;

    @Schema(
            description = "Cards information of the customer"
    )
    @NotEmpty(message = "Account number must not be empty")
    private CardsDto cardsDto;

    @Schema(
            description = "Loans information of the customer"
    )
    @NotEmpty(message = "Account number must not be empty")
    private LoansDto loansDto;
}
