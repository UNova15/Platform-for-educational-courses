package org.platform.platformforeducationalcourses.dto.auth;

import org.platform.platformforeducationalcourses.domain.user.Role;

public record ParsedToken(long userId, String login, Role role) {}
