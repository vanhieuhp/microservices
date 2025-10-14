package hieunv.dev.accounts.command.controller;

import hieunv.dev.accounts.command.CreateCustomerCommand;
import hieunv.dev.accounts.command.DeleteCustomerCommand;
import hieunv.dev.accounts.command.UpdateCustomerCommand;
import hieunv.dev.accounts.constants.CustomerConstants;
import hieunv.dev.accounts.dto.CustomerDto;
import hieunv.dev.accounts.dto.ResponseDto;
import hieunv.dev.accounts.util.GeneratorUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        long randomAccNumber = GeneratorUtils.generateAccountNumber();

        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand.builder()
                .customerId(randomAccNumber)
                .mobileNumber(customerDto.getMobileNumber())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .activeSw(CustomerConstants.ACTIVE_SW)
                .build();

        commandGateway.sendAndWait(createCustomerCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        UpdateCustomerCommand updateCustomerCommand = UpdateCustomerCommand.builder()
                .customerId(customerDto.getCustomerId())
                .mobileNumber(customerDto.getMobileNumber())
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .activeSw(CustomerConstants.ACTIVE_SW)
                .build();

        commandGateway.sendAndWait(updateCustomerCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam("customerId") Long customerId) {
        DeleteCustomerCommand deleteCustomerCommand = DeleteCustomerCommand.builder()
                .customerId(customerId)
                .activeSw(CustomerConstants.IN_ACTIVE_SW)
                .build();

        commandGateway.sendAndWait(deleteCustomerCommand);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }
}
