package org.platform.platformforeducationalcourses.dto.auth.registration;

import refactor.auth.implemetnation.domain.user.valueobject.UserRole;

public record RegistrationDto(String login, String password, UserRole role) {}
