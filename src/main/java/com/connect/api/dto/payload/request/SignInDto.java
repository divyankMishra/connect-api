package com.connect.api.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
    @NotBlank(message = "Please provide username")
    private String username;
    @NotBlank(message = "Please provide password")
    private String password;
}
