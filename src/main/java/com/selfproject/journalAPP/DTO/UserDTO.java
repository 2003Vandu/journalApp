package com.selfproject.journalAPP.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    @NotBlank
    @Schema(description = "Enter UserName")
    private String userName;
    @NotBlank
    @Schema(description = "Enter email@.com")
    private String email;

    @Schema(description = "Enter SentimentalAnalysis")
    private boolean SentimentalAnalysis;

    @NotBlank
    @Schema(description = "Enter password")
    private String password;
}
