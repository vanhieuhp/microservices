package hieunv.dev.accounts.controller;

import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.ErrorResponseDto;
import hieunv.dev.accounts.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Tag(
        name = "CRUD REST APIs for Customer in EasyBank",
        description = "CRUD REST APIs in EasyBank to Create, Update, Fetch and DELETE Customer details"
)
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/customers")
@RestController
@AllArgsConstructor
@Configuration
public class CustomerController {

    private CustomerService customerService;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestParam @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber,
            @RequestHeader(name = "easybank-correlation-id") String correlationId) {
        log.debug("easyBank-correlation-if found: {}", correlationId);

        CustomerDetailsDto cardsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }
}
