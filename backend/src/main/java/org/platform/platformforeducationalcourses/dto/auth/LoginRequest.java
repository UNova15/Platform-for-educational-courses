package org.platform.platformforeducationalcourses.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Логин не может быть пустым") String login,

        @NotBlank(message = "Пароль не может быть пустым") String password) {}
