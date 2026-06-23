package org.platform.platformforeducationalcourses.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT,
    TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
