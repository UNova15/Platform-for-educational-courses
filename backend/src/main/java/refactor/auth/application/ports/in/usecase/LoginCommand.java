package refactor.auth.application.ports.in.usecase;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Login cannot be empty") String login,

        @NotBlank(message = "Password cannot be empty") String password) {}
