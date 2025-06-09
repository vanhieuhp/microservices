package hieunv.dev.accounts.controller;

import hieunv.dev.accounts.constants.AccountConstants;
import hieunv.dev.accounts.dto.AccountsContactInfoDto;
import hieunv.dev.accounts.dto.CustomerDto;
import hieunv.dev.accounts.dto.ErrorResponseDto;
import hieunv.dev.accounts.dto.ResponseDto;
import hieunv.dev.accounts.service.impl.AccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@Tag(
        name = "CRUD REST APIs for Accounts in EasyBank",
        description = "CRUD REST APIs in EasyBank to Create, Update, Fetch and DELETE account details"
)
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/accounts")
@RestController
@AllArgsConstructor
@Configuration
@Log4j2
public class AccountController {

    private AccountService accountService;

    @Operation(
            summary = "Create Account REST API",
            description = "Create Account REST API in EasyBank to create new account for a customer"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto requestBody) {
        accountService.createAccount(requestBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> getAccount(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account updated successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto requestBody) {
        boolean isUpdated = accountService.updateAccount(requestBody);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Retry(name = "getContactInfo", fallbackMethod = "getContactInfoFallback")
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() throws TimeoutException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
//        log.debug("Get Contact Info API invoked by client");
//        throw new TimeoutException();
    }

    public ResponseEntity<String> getContactInfoFallback(Throwable throwable) {
        log.debug("get contact info fallback() method revoke");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

    @RateLimiter(name = "build-info", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("21");
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("17");
    }
}
