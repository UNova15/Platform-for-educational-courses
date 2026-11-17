package kira.user.adapter.out.persistence.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users", schema = "account")
record UserEntity(
        @Id @Column("id") Long id,
        @Column("username") String username,
        @Column("email") String email,
        @Column("password") String password) {}
