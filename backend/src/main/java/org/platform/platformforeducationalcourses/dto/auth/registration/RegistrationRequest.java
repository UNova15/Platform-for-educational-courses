package org.platform.platformforeducationalcourses.dto.auth.registration;

import static org.platform.platformforeducationalcourses.domain.constant.UserValidationConstants.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.user.UserRole;

public record RegistrationRequest(
        @NotBlank(message = "The username cannot be empty")
        @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH, message = "The username has an incorrect length")
        String login,

        @NotBlank(message = "The password cannot be empty")
        @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = "The password has an incorrect length")
        String password,

        @NotNull(message = "У пользователя должна быть роль")
        UserRole role) {}
