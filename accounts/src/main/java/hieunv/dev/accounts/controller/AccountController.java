package hieunv.dev.accounts.controller;

import hieunv.dev.accounts.constants.AccountConstants;
import hieunv.dev.accounts.dto.AccountContactInfoDto;
import hieunv.dev.accounts.dto.AccountDto;
import hieunv.dev.accounts.dto.ResponseDto;
import hieunv.dev.accounts.entity.Account;
import hieunv.dev.accounts.service.impl.AccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;
    
    private final AccountService accountService;

    @Retry(name = "getContactInfo", fallbackMethod = "getContactInfoFallback")
    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() throws TimeoutException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
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

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber()); // Will be generated if null
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        account.setMobileNumber(accountDto.getMobileNumber());
        
        AccountDto createdAccount = accountService.createAccount(account);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAccount);
    }

    @GetMapping("/fetch")
    public ResponseEntity<AccountDto> fetchAccount(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        AccountDto accountDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDto);
    }

    @GetMapping("/fetch/{accountNumber}")
    public ResponseEntity<AccountDto> fetchAccountById(@PathVariable Long accountNumber) {
        AccountDto accountDto = accountService.fetchAccountById(accountNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody AccountDto accountDto) {
        boolean isUpdated = accountService.updateAccount(accountDto);
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

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable Long accountNumber) {
        boolean isDeleted = accountService.deleteAccount(accountNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }
}
