package kira.user.adapter.out.persistence.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "refresh_tokens", schema = "account")
@Getter
@AllArgsConstructor
class RefreshTokenEntity {
    @Id
    private final Long id;

    private final Long userId;
    private final String token;
}
