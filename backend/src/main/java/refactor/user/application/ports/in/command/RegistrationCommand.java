package refactor.user.application.ports.in.command;

import static org.platform.platformforeducationalcourses.properties.constatnts.UserValidationConstants.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import refactor.user.domain.user.UserRole;

public record RegistrationCommand(
        @NotBlank(message = "The username cannot be empty")
        @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH, message = "The username has an incorrect length")
        String login,

        @NotBlank(message = "The password cannot be empty")
        @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = "The password has an incorrect length")
        String password,

        @NotNull(message = "The user must have a role") UserRole role) {}
