package com.durga.loans.controller;

import com.durga.loans.constants.LoanConstants;
import com.durga.loans.dto.ErrorResponseDto;
import com.durga.loans.dto.LoanDto;
import com.durga.loans.dto.ResponseDto;
import com.durga.loans.serviceInt.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts Microservice in JustIn Bank",
        description = "CRUD REST APIs in JustIn Bank to CREATE, FETCH, UPDATE and DELETE account details"
)
public class LoanController {

    private ILoanService iLoanService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan in JustIn Bank"
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan Details based on mobile number"
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
    @GetMapping("/fetch")
    public ResponseEntity<ResponseDto> fetchLoan(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        iLoanService.fetchLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update Loan Details based on loan number"
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
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto){
        boolean isUpdated = iLoanService.updateLoan(loanDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan Details based on mobile number"
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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        boolean isdeleted = iLoanService.deleteLoan(mobileNumber);
        if(isdeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
        }
    }

}
