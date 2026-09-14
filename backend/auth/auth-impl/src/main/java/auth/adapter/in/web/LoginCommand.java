package auth.adapter.in.web;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Login cannot be empty") String login,

        @NotBlank(message = "Password cannot be empty") String password) {}
