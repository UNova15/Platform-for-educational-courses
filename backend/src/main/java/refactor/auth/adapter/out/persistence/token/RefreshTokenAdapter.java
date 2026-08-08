package refactor.auth.adapter.out.persistence.token;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;
import refactor.auth.domain.token.HashedToken;
import refactor.auth.domain.token.RefreshToken;

// TODO исправить на Valkey вместо бд (+ в данный момент нет контроля ttl)

@Repository
@RequiredArgsConstructor
class RefreshTokenAdapter implements RefreshTokenRepositoryPort {
    private final OrmRefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public RefreshToken save(RefreshToken token) {
        RefreshTokenEntity entity = mapper.toEntity(token);
        RefreshTokenEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void remove(RefreshToken token) {
        repository.deleteById(token.getId());
    }

    @Override
    public Optional<RefreshToken> load(HashedToken token) {
        return repository.findByToken(token.getHashedToken()).map(mapper::toDomain);
    }
}
