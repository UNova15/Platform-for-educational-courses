package org.platform.platformforeducationalcourses.dto.auth.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Login cannot be empty") String login,

        @NotBlank(message = "Password cannot be empty") String password) {}
