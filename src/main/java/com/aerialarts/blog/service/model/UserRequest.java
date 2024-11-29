package com.aerialarts.blog.service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequest {

    @NotNull(message = "The username must be provided.")
    private String username;

    @Email
    @NotNull(message = "The email must be provided.")
    private String email;

    @NotNull(message = "The password must be provided.")
    private String password;
}
