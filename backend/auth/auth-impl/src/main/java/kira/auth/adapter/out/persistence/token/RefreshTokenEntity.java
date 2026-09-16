package kira.auth.adapter.out.persistence.token;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "refresh_tokens")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
class RefreshTokenEntity {
    @Id
    private final Long id;

    private final Long userId;
    private final String token;
}
