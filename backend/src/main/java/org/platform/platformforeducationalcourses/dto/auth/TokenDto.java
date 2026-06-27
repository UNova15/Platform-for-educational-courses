package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.UserRole;

public record TokenDto(long id, String login, UserRole role, String jwtToken, String refreshToken) {}
