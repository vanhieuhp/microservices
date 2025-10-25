package hieunv.dev.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileNumberUpdate {

    @NotEmpty(message = "currentMobileNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "currentMobileNumber must be 10 digits")
    private String currentMobileNumber;

    @NotEmpty(message = "newMobileNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "newMobileNumber must be 10 digits")
    private String newMobileNumber;
}
