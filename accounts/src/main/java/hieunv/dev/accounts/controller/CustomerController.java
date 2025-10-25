package hieunv.dev.accounts.controller;

import hieunv.dev.accounts.constants.CustomerConstants;
import hieunv.dev.accounts.dto.CustomerDetailsDto;
import hieunv.dev.accounts.dto.CustomerDto;
import hieunv.dev.accounts.dto.ErrorResponseDto;
import hieunv.dev.accounts.dto.MobileNumberUpdate;
import hieunv.dev.accounts.dto.ResponseDto;
import hieunv.dev.accounts.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        log.debug("fetchCustomerDetails method start");
        CustomerDetailsDto cardsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        log.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam
                                                     @Pattern(regexp="(^$|[0-9]{10})",
                                                             message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        CustomerDto customerDto = customerService.fetchCustomer(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = customerService.updateCustomer(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CustomerConstants.STATUS_500, CustomerConstants.MESSAGE_500_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam("customerId") Long customerId) {
        boolean isDeleted = customerService.deleteCustomer(customerId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CustomerConstants.STATUS_500, CustomerConstants.MESSAGE_500_DELETE));
        }
    }

    @PutMapping("/mobile-number")
    public ResponseEntity<Boolean> updateMobileNumber(@RequestBody @Valid MobileNumberUpdate mobileNumberUpdate) {
        boolean isUpdated = customerService.updateMobileNumber(mobileNumberUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
    }
}
