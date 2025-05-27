package hieunv.dev.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Mobile number must not be empty")
    @Size(min = 5, max=30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @Pattern(regexp = "^$|[0-9]{10}", message = "AccountNumber must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Account number must not be empty")
    private AccountDto accountDto;
}
