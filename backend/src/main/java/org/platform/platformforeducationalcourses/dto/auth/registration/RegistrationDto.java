package org.platform.platformforeducationalcourses.dto.auth.registration;

import refactor.auth.domain.user.valueobject.UserRole;

public record RegistrationDto(String login, String password, UserRole role) {}
