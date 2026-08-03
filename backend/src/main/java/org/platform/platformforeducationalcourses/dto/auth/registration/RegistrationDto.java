package org.platform.platformforeducationalcourses.dto.auth.registration;

import refactor.user.domain.user.UserRole;

public record RegistrationDto(String login, String password, UserRole role) {}
