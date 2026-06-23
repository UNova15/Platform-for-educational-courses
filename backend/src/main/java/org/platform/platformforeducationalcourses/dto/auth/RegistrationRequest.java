package org.platform.platformforeducationalcourses.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.user.Role;

//TODO пока удалил доп информацию о пользователе
public record RegistrationRequest(
        @NotBlank(message = "Логин не может быть пустым")
        @Size(min = 4, max = 30, message = "Длина логина должна быть от 4 до 30 символов")
        String login,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, message = "Длина пароля должна быть более 8 символов")
        String password,

        @NotNull(message = "У пользователя должна быть роль")
        Role role) {
}
