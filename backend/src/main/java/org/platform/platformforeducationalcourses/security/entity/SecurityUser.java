package org.platform.platformforeducationalcourses.security.entity;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.domain.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUser implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";

    @Getter
    private final long id;

    private final String login;
    private final String password;

    @Getter
    private final UserRole role;

    public static SecurityUser fromUser(User user) {
        return new SecurityUser(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }

    public static SecurityUser fromJwt(long id, String login, UserRole role) {
        return new SecurityUser(id, login, null, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX.concat(role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
