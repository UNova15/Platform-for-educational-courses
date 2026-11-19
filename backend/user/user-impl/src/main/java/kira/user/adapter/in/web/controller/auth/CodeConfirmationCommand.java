package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.constraints.NotBlank;

public record CodeConfirmationCommand(
        @NotBlank String email, @NotBlank String code) {}
