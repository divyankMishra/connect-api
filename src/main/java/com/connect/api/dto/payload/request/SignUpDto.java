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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ConfirmPassword
public class SignUpDto {
    @NotBlank(message = "Please provide username.")
    @Size(max = 50, message = "Username can not exceed 50 characters.")
    @Pattern(regexp = "^[a-zA-Z\\d]+$",message = "Only alphanumeric characters are allowed in username.")
    @DuplicateUsername
    private String username;
    @NotBlank(message = "Please provide firstname.")
    private String firstname;
    private String lastname;
    @NotBlank(message = "Please provide email.")
    @Size(max = 50, message = "Email can not exceed 50 characters.")
    @Email(message = "Please provide proper email.")
    @DuplicateEmail
    private String email;
    @NotBlank(message = "Password can not be blank.")
    private String password;
    @NotBlank(message = "Password can not be blank.")
    private String confirmPassword;
}
