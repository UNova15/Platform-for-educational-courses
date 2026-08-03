package refactor.user.application.ports.in.command;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Login cannot be empty") String login,

        @NotBlank(message = "Password cannot be empty") String password) {}
