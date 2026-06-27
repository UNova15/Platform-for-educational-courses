package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.UserRole;

public record ParsedToken(long userId, String login, UserRole role) {}
