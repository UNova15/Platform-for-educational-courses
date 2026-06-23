package org.platform.platformforeducationalcourses.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUser implements UserDetails {
    @Getter
    private final long id;
    private final String login;
    private final String password;
    @Getter
    private final Role role;

    public static SecurityUser fromUser(User user) {
        return new SecurityUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static SecurityUser fromJwt(long id, String login, Role role) {
        return new SecurityUser(id, login, null, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
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
