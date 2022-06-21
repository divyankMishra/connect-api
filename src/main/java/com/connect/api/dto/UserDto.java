package com.connect.api.dto;

import com.connect.api.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    @JsonIgnore
    private Long id;
    @NotBlank(message = "username.null")
    private String username;
    @NotBlank(message = "firstname.null")
    private String firstname;
    private String lastname;
    @NotBlank(message = "email.null")
    @Email(message = "email.improper")
    private String email;
    @NotNull
    @ToString.Exclude
    @JsonIgnore
    private Date createdAt;
    @ToString.Exclude
    @JsonIgnore
    private Date updatedAt;
    @ToString.Exclude
    @JsonIgnore
    private String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname=user.getLastname();
        this.email = user.getEmail();
    }
}
