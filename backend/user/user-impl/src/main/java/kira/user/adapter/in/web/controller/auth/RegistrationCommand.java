package kira.user.adapter.in.web.controller.auth;

import static kira.user.domain.user.valueobject.RawPassword.MAX_PASSWORD_LENGTH;
import static kira.user.domain.user.valueobject.RawPassword.MIN_PASSWORD_LENGTH;
import static kira.user.domain.user.valueobject.Username.MAX_LOGIN_LENGTH;
import static kira.user.domain.user.valueobject.Username.MIN_LOGIN_LENGTH;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationCommand(
        @NotBlank(message = "The username cannot be empty")
        @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH, message = "The username has an incorrect length")
        String username,

        @NotBlank(message = "The email cannot be empty") @Email(message = "Incorrect email format")
        String email,

        @NotBlank(message = "The password cannot be empty")
        @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = "The password has an incorrect length")
        String password) {}
