package com.connect.api.dto.payload.request;

import com.connect.api.annotation.ConfirmPassword;
import com.connect.api.annotation.DuplicateEmail;
import com.connect.api.annotation.DuplicateUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ConfirmPassword
public class SignUpDto {
    @NotBlank(message = "username.null")
    @Size(max = 50, message = "username.size")
    @DuplicateUsername
    private String username;
    @NotBlank(message = "firstname.null")
    private String firstname;
    private String lastname;
    @NotBlank(message = "email.null")
    @Size(max = 50, message = "username.size")
    @Email(message = "email.improper")
    @DuplicateEmail
    private String email;
    @NotBlank(message = "password.null")
    private String password;
    @NotBlank(message = "confirmPassword.null")
    private String confirmPassword;
}
