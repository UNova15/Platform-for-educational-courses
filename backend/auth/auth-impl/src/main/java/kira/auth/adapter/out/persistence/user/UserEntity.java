package kira.auth.adapter.out.persistence.user;

import kira.auth.domain.user.valueobject.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
class UserEntity {
    @Id
    private final Long id;

    private String login;
    private String password;
    private final UserRole role;
}
