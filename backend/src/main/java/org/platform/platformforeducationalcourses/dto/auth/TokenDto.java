package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.Role;

public record TokenDto(long id, String login, Role role, String jwtToken, String refreshToken) {}
