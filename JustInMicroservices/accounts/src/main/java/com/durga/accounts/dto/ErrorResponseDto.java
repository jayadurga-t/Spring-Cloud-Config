package com.durga.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "Api path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error occurred"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error occurred"
    )
    private String errorMessage;

    @Schema(
            description = "Error time representing the error occurred"
    )
    private LocalDateTime errorTime;

}
