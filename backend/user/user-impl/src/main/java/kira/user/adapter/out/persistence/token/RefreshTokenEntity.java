package kira.user.adapter.out.persistence.token;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "refresh_tokens", schema = "account")
record RefreshTokenEntity(
        @Id @Column("id") Long id,
        @Column("user_id") Long userId,
        @Column("token") String token) {}
