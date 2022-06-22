package com.connect.api.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequestPayloadDto {
    @NotBlank(message = "Username is required for this operation")
    @Size(message = "Invalid Username!")
    @Pattern(regexp = "^[a-zA-Z\\d]+$", message = "Invalid Username!")
    private String username;
}
