package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RepeatCodeCommand(@NotBlank @Email String email) {}
