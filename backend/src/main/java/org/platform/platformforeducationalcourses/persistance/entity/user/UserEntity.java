package org.platform.platformforeducationalcourses.persistance.entity.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.domain.user.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    private final Long id;

    private String login;
    private String password;
    private final UserRole role;

    public static UserEntity fromUser(User user){
        return new UserEntity(user.getId(),user.getLogin(),user.getPassword(),user.getRole());
    }
}
