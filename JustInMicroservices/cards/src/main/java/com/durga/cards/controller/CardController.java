package com.durga.cards.controller;

import com.durga.cards.constants.CardConstants;
import com.durga.cards.dto.CardDto;
import com.durga.cards.dto.ErrorResponseDto;
import com.durga.cards.dto.ResponseDto;
import com.durga.cards.serviceInt.ICardService;
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
@RequestMapping("/api")
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts Microservice in JustIn Bank",
        description = "CRUD REST APIs in JustIn Bank to CREATE, FETCH, UPDATE and DELETE account details"
)
public class CardController {

    private ICardService iCardService;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card in JustIn Bank"
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
    @PostMapping("/createCard")
    public ResponseEntity<ResponseDto> createCard(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        iCardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Card REST API",
            description = "REST API to fetch new Card Details based on mobile number"
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
    @GetMapping("/fetchCard")
    public ResponseEntity<ResponseDto> fetchCard(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        iCardService.fetchCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update Card Details based on card number"
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
    @PutMapping("/updateCard")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardDto cardDto){
        boolean isUpdated=iCardService.updateCard(cardDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to update Card Details based on mobile number"
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
    @DeleteMapping("/deleteCard")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        boolean isDeleted=iCardService.deleteCard(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
        }
    }
}
