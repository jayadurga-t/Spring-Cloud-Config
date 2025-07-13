package com.durga.accounts.controller;

import com.durga.accounts.constants.AccountConstants;
import com.durga.accounts.dto.CustomerDto;
import com.durga.accounts.dto.ErrorResponseDto;
import com.durga.accounts.dto.ResponseDto;
import com.durga.accounts.serviceInt.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts Microservice in JustIn Bank",
        description = "CRUD REST APIs in JustIn Bank to CREATE, FETCH, UPDATE and DELETE account details"
)
public class AccountController {

    private IAccountService iAccountService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside JustIn Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer & Account Details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchAccountDetails")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @NotEmpty(message = "Mobile number can not be a null or empty") @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iAccountService.fetchAccountDetails(mobileNumber));
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer & Account Details based on account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/updateAccountDetails")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated=iAccountService.updateAccountDetails(customerDto);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account Details",
            description = "REST API to delete Customer & Account Details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/deleteAccountDetails")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @NotEmpty(message = "Mobile number can not be a null or empty") @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        boolean isdeleted=iAccountService.deleteAccount(mobileNumber);
        if(isdeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }
}
