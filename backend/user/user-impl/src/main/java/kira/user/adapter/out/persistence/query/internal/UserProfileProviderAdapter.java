package kira.user.adapter.out.persistence.query.internal;

import java.util.List;
import java.util.Optional;
import kira.user.application.ports.out.persistance.query.internal.UserProfileProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProfileProviderAdapter implements UserProfileProviderPort {
    private final JdbcClient dbClient;

    @Override
    public List<String> getUsersEmailByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        return dbClient.sql("SELECT email FROM account.users WHERE id IN (:ids)")
                .param("ids", ids)
                .query(String.class)
                .list();
    }

    @Override
    public Optional<String> getUserNameById(long userId) {
        return dbClient.sql("SELECT login FROM account.users WHERE id = :userId")
                .param("userId", userId)
                .query(String.class)
                .optional();
    }
}
