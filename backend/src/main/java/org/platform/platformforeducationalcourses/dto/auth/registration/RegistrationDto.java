package org.platform.platformforeducationalcourses.dto.auth.registration;

import org.platform.platformforeducationalcourses.domain.user.UserRole;

public record RegistrationDto(String login, String password, UserRole role) {}
