package kira.auth.adapter.out.persistence.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users", schema = "auth")
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
class UserEntity {
    @Id
    private final Long id;

    private final String login;
    private final String password;
}
