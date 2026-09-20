package kira.auth.adapter.out.persistence.user;

import kira.auth.domain.user.valueobject.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "auth.users")
@Getter
@AllArgsConstructor
class UserEntity {
    @Id
    private final Long id;

    private final String login;
    private final String password;
    private final UserRole role;
}
