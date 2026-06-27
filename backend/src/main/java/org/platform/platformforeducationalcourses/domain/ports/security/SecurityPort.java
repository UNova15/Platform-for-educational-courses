package org.platform.platformforeducationalcourses.domain.ports.security;

import org.platform.platformforeducationalcourses.domain.user.User;

public interface SecurityPort {
    User identify(String login, String password);
}
